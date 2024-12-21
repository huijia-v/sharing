package com.huijia.sharing.core.exception.file.operator;

import com.huijia.sharing.core.exception.StorageSourceException;
import com.huijia.sharing.core.util.CodeMsg;

/**
 * 禁止服务器代理下载异常
 *
 * @author zhaojun
 */
public class DisableProxyDownloadException extends StorageSourceException {
	
	public DisableProxyDownloadException(CodeMsg codeMsg, Integer storageId) {
		super(codeMsg, storageId, null);
	}
	
	public DisableProxyDownloadException(CodeMsg codeMsg, Integer storageId, String message) {
		super(codeMsg, storageId, message);
	}
	
	public DisableProxyDownloadException(CodeMsg codeMsg, Integer storageId, String message, Throwable cause) {
		super(codeMsg, storageId, message, cause);
	}
}