package com.huijia.sharing.core.exception;

/**
 * 存储源不支持代理上传异常
 *
 * @author zhaojun
 */
public class StorageSourceNotSupportProxyUploadException extends ZFileRuntimeException {

	public StorageSourceNotSupportProxyUploadException(String message) {
		super(message);
	}

}