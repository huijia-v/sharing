package com.huijia.sharing.core.exception.file.operator;

import com.huijia.sharing.core.exception.StorageSourceException;
import com.huijia.sharing.core.util.CodeMsg;
import lombok.Getter;

/**
 * 存储源文件操作异常
 * @author zhaojun
 */
@Getter
public class StorageSourceFileOperatorException extends StorageSourceException {
	
	public StorageSourceFileOperatorException(CodeMsg codeMsg, Integer storageId, String message, Throwable cause) {
		super(codeMsg, storageId, message, cause);
	}
	
}