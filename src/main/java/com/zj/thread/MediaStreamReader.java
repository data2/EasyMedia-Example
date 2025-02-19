package com.zj.thread;

import java.io.InputStream;

import org.bytedeco.javacv.FFmpegFrameGrabber;

import lombok.Getter;
import lombok.Setter;

/**
 * 提供管道流接入
 * @author ZJ
 *
 */
public class MediaStreamReader {

	@Getter@Setter
	private InputStream in;

	public MediaStreamReader(InputStream in) {
		super();
		this.in = in;
	}
	
	public void init() {
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(in);
		
		/**
		 * 待完善
		 */
	}
}
