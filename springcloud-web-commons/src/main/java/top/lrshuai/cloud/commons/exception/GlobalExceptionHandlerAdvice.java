package top.lrshuai.cloud.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.Result;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {
    /**
     * 参数验证异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg =  e.getBindingResult().getFieldError().getDefaultMessage();
        if (!StringUtils.isEmpty(errorMsg)) {
            return Result.error(errorMsg);
        }
        log.error(e.getMessage(),e);
        return Result.error(e.getMessage());
    }

    /**
     * 参数验证异常
     */
    @ExceptionHandler(value = ValidationException.class)
    public Result constraintViolationException(ValidationException e) {
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException err = (ConstraintViolationException) e;
            ConstraintViolation<?> constraintViolation = err.getConstraintViolations().stream().findFirst().get();
            String messageTemplate = constraintViolation.getMessageTemplate();
            if(!StringUtils.isEmpty(messageTemplate)){
                return Result.error(messageTemplate);
            }
        }
        log.error(e.getMessage(),e);
        return Result.error(e.getMessage());
    }

    /**
     * 默认异常
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public Result authorityException(Exception e) {
        log.error("系统异常:" + e.getMessage(), e);
        return Result.error(ApiResultEnum.AUTH_ERR);
    }

    /**
     * 默认异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result defaultException(Exception e) {
        log.error("系统异常:" + e.getMessage(), e);
        return Result.error();
    }
}
