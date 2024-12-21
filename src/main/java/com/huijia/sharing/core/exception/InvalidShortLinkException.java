package com.huijia.sharing.core.exception;

/**
 * 无效的直链异常
 *
 * @author zhaojun
 */
public class InvalidShortLinkException extends ZFileRuntimeException {

	public InvalidShortLinkException(String message) {
		super(message);
	}

}