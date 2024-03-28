package top.lrshuai.common.security.aspect;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import top.lrshuai.common.core.constant.SecurityConst;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.exception.ServiceException;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.utils.ServletUtils;
import top.lrshuai.common.redis.config.enums.RedisKeyEnum;
import top.lrshuai.common.redis.config.util.RedisUtils;
import top.lrshuai.common.security.annotation.RepeatSubmit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 防止重复提交
 */
@Aspect
@Component
public class RepeatSubmitAspect {

    private static final TransmittableThreadLocal<String> KEY_CACHE = new TransmittableThreadLocal<>();

    @Before("@annotation(repeatSubmit)")
    public void doBefore(JoinPoint point, RepeatSubmit repeatSubmit) throws Throwable {
        // 如果注解不为0 则使用注解数值
        long interval = repeatSubmit.timeUnit().toMillis(repeatSubmit.interval());
        if (interval < 1000) {
            throw new ServiceException(ApiResultEnum.SYSTEM_REPEAT_SUBMIT_TIME_LIMIT);
        }
        HttpServletRequest request = ServletUtils.getRequest();
        String nowParams = argsArrayToString(point.getArgs());
        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();
        // 唯一值（没有消息头则使用请求地址）
        String submitKey = StringUtils.trimToEmpty(request.getHeader(SecurityConst.TOKEN));
        submitKey = SecureUtil.md5(submitKey + ":" + nowParams);
        // 唯一标识（指定key + url + 消息头）
        String cacheRepeatKey = RedisKeyEnum.REPEAT_SUBMIT.getKey() + url + submitKey;
        if (RedisUtils.setObjectIfAbsent(cacheRepeatKey, "", Duration.ofMillis(interval))) {
            KEY_CACHE.set(cacheRepeatKey);
        } else {
            throw new ServiceException(ApiResultEnum.SYSTEM_REPEAT_SUBMIT);
        }
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(repeatSubmit)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Object jsonResult) {
        if (jsonResult instanceof R<?>) {
            R<?> r = (R<?>) jsonResult;
            try {
                // 成功则不删除redis数据 保证在有效时间内无法重复提交
                if (r.isSuccess()) {
                    return;
                }
                RedisUtils.deleteObject(KEY_CACHE.get());
            } finally {
                KEY_CACHE.remove();
            }
        }
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(repeatSubmit)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Exception e) {
        RedisUtils.deleteObject(KEY_CACHE.get());
        KEY_CACHE.remove();
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtil.isEmpty(paramsArray)) {
            return params.toString();
        }
        for (Object o : paramsArray) {
            if (ObjectUtil.isNotNull(o) && !isFilterObject(o)) {
                params.add(JSON.toJSONString(o));
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
            || o instanceof BindingResult;
    }

}
