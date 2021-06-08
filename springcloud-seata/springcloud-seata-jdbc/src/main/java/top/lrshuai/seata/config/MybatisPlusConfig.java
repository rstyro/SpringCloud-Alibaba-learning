package top.lrshuai.seata.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;


@EnableTransactionManagement
@Configuration
//@MapperScan("top.lrshuai.seata.*.mapper*")
public class MybatisPlusConfig implements MetaObjectHandler {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 自动填充功能
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createTime",LocalDateTime.now(),metaObject);
        this.setInsertFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }

}
