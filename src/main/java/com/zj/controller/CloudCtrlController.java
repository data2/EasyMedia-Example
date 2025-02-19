package com.zj.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zj.ctrl.CameraCtrl;
import com.zj.ctrl.CloudCode;
import com.zj.ctrl.Control;
import com.zj.ctrl.LoginPlay;
import com.zj.ctrl.MyNativeLong;
import com.zj.ctrl.TempData;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

/**
 * 云台控制
 * @author ZJ
 *
 */
@RequestMapping("hk")
@RestController
public class CloudCtrlController {
	
//	http://localhost:8888/hk/ctrl?ip=192.168.2.120&op=left&username=admin&password=VZCDOY
	
	/**
	 * 云台控制接口
	 * @param session
	 * @param camera
	 */
	@RequestMapping("ctrl")
	public void cloudCtrl(HttpSession session, CameraCtrl camera) {
		checkLogin(camera);
		session.setAttribute("ip", camera.getIp());

		// 截取摄像机实时图片
//		boolean imgSavePath = Control.getImgSavePath(camera.getIp(), "D:\\tempFile\\3.jpg");

		if (StrUtil.equals(camera.getOp(), "up")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.TILT_UP, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.TILT_UP, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "down")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.TILT_DOWN, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.TILT_DOWN, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "left")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.PAN_LEFT, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.PAN_LEFT, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "right")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.PAN_RIGHT, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.PAN_RIGHT, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "left_up")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.UP_LEFT, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.UP_LEFT, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "left_down")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.DOWN_LEFT, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.DOWN_LEFT, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "right_up")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.UP_RIGHT, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.UP_RIGHT, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "right_down")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.DOWN_RIGHT, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.DOWN_RIGHT, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "big")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.ZOOM_IN, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.ZOOM_IN, CloudCode.SPEED_LV6, CloudCode.END);
		} else if (StrUtil.equals(camera.getOp(), "small")) {
			// 制摄像机云台控制(开启)
			Control.cloudControl(camera.getIp(), CloudCode.ZOOM_OUT, CloudCode.SPEED_LV6, CloudCode.START);
			try {
				// 让云台运行1000ms
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// 制摄像机云台控制(关闭)
			Control.cloudControl(camera.getIp(), CloudCode.ZOOM_OUT, CloudCode.SPEED_LV6, CloudCode.END);
		}

	}
	
	/**
	 * sdk登入
	 */
	private void checkLogin(CameraCtrl camera) {
		MyNativeLong nativeLong = TempData.getTempData().getNativeLong(camera.getIp());
		if(null == nativeLong) {
			LoginPlay lp = new LoginPlay();
			// 输入摄像机ip，端口，账户，密码登录
			try {
				boolean doLogin = lp.doLogin(camera.getIp(), Convert.toShort(camera.getPort(), (short) 8000), camera.getUsername(),
						camera.getPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
