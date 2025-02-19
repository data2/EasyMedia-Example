package com.zj.init;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bytedeco.javacpp.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.zj.common.MediaConstant;
import com.zj.dto.CameraDto;
import com.zj.entity.Camera;
import com.zj.mapper.CameraMapper;
import com.zj.server.MediaServer;
import com.zj.service.HlsService;
import com.zj.service.MediaService;
import com.zj.thread.MediaListenThread;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 启动流媒体
 * 
 * @author ZJ
 *
 */
@Slf4j
@Component
public class InitServer implements CommandLineRunner {

	@Value("${mediaserver.port}")
	private int port;

	@Autowired
	private MediaServer mediaServer;

	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private HlsService hlsService;

	@Autowired
	private CameraMapper cameraMapper;
	
	@Autowired
	private Environment env;

	@Override
	public void run(String... args) throws Exception {
		initAutoPlay();
		ThreadUtil.execute(new MediaListenThread());
        String ip = InetAddress.getLocalHost().getHostAddress();
        String httpPort = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if (StrUtil.isEmpty(path)) {
            path = "";
        }
        log.info("\n--------------------------------------------------------- \n" +
                "\t EasyMedia is running! Access address: \n" +
                "\t media port at : \t {} \n" +
                "\t http port at : \t {} \n" +
                "\t web Local: \t http://localhost:{} \n" +
                "\t web External: \t http://{}:{}{} \n" +
                "\t httpflv: \t http://{}:{}/live?url={您的源地址} \n" +
                "\t wsflv: \t ws://{}:{}/live?url={您的源地址} \n" +
                "\t hls(m3u8): \t http://{}:{}/hls?url={您的源地址} \n" +
                "\t h2-database: \t http://{}:{}/h2-console \n" +
                "--------------------------------------------------------- \n", 
                port, 
                httpPort, 
                httpPort, 
                ip, httpPort, path, 
                ip, port, 
                ip, port, 
                ip, httpPort,
                ip, httpPort);
		mediaServer.start(new InetSocketAddress("0.0.0.0", port));
	}

	/**
	 * 启动初始化自动拉流（已保存的流，如果已经开启，会自动拉流）
	 */
	public void initAutoPlay() {
		List<Camera> selectList = cameraMapper.selectList(null);
		if (null != selectList && !selectList.isEmpty()) {
			log.info("已启动自动拉流！");
			
			for (Camera camera : selectList) {
				//已启用的自动拉流，不启用的不自动拉
				CameraDto cameraDto = new CameraDto();
				cameraDto.setUrl(camera.getUrl());
				cameraDto.setAutoClose(false);
				cameraDto.setEnabledFFmpeg(camera.getFfmpeg() == 1 ? true : false);
				cameraDto.setEnabledFlv(camera.getFlv() == 1 ? true : false);
				cameraDto.setEnabledHls(camera.getHls() == 1 ? true : false);
				cameraDto.setMediaKey(camera.getMediaKey());
				
				if(camera.getFlv() == 1) {
					mediaService.playForApi(cameraDto);
				}
				if (camera.getHls() == 1) {
					hlsService.startConvertToHls(cameraDto);
				}
			}

		} 
		
		log.info("您还可以通过restful api添加或删除流！");
	}

	/**
	 * 提前初始化，可避免推拉流启动耗时太久
	 */
	@PostConstruct
	public void loadFFmpeg() {
		//javacv 1.5.5 以上已经优化加载，这边可以省略了
//		try {
//			log.info("正在初始化资源，请稍等...");
//			FFmpegFrameGrabber.tryLoad();
//			FFmpegFrameRecorder.tryLoad();
//		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
//			e.printStackTrace();
//		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
//			e.printStackTrace();
//		}

		/**
		 * 初始化ffmpeg路径
		 */
		String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
		System.setProperty(MediaConstant.ffmpegPathKey, ffmpeg);
		log.info("初始化资源成功");
	}
}
