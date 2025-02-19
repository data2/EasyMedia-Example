package com.zj.ctrl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraCtrl {

	private String ip;
	private int port = 8000;	//海康默认端口
	private String username;
	private String password;
	private String op;
}
