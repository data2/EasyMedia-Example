package com.zj.common;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * @author ZJ
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public AjaxResult globalException(HttpServletResponse response, RuntimeException ex) {
		log.info("请求错误：" + ex.getMessage());
		return AjaxResult.error(ex.getMessage());
	}

}