package top.lrshuai.nacos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.Result;

/**
 * 全局异常捕获
 * @author rstyro
 *
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

	@ExceptionHandler(NullPointerException.class)
	public Result NullPointer(NullPointerException ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.FAILED);
	}

	@ExceptionHandler(RuntimeException.class)
	public Result RuntimeException(RuntimeException ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR);
	}

	@ExceptionHandler(Exception.class)
	public Result exception(Exception ex){
		logger.error(ex.getMessage(),ex);
		return Result.error(ApiResultEnum.ERROR);
	}
	
}
