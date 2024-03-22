package top.lrshuai.common.mybatis.handler;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import top.lrshuai.common.core.context.SecurityContextHolder;
import top.lrshuai.common.core.exception.ServiceException;

import java.time.LocalDateTime;

/**
 * mybatis-plus 字段自动填充功能
 */
@Slf4j
public class DefaultFieldAutoFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            this.strictInsertFill(metaObject, "createBy", Long.class, SecurityContextHolder.getUserId());
            this.strictInsertFill(metaObject, "updateBy", Long.class, SecurityContextHolder.getUserId());
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        } catch (Exception e) {
            throw new ServiceException("insertFill-自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            this.strictUpdateFill(metaObject, "updateBy", Long.class, SecurityContextHolder.getUserId());
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        } catch (Exception e) {
            throw new ServiceException("updateFill-自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }


}
