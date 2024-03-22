package top.lrshuai.common.seata.aspect;

import cn.hutool.core.util.StrUtil;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class GlobalTransactionalAspect {

    /**
     * 用于处理feign降级后无法触发seata的全局事务的回滚
     * 过程：拦截*FallbackFactory降级方法，只要进了这个方法就手动结束seata全局事务
     * top.lrshuai.mall.api.feign.fallback
     */
    @After("execution(* top.lrshuai..*Factory.*(..))")
    public void before(JoinPoint joinPoint) throws TransactionException {
        log.info("执行全局事务切片");
        if (!StrUtil.isBlank(RootContext.getXID())) {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            log.info("\n===>降级后进行全局事务手动回滚,class={},method={},全局事务ID={}", className, methodName, RootContext.getXID());
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }
    }
}
