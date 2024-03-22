package top.lrshuai.common.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.lrshuai.common.security.interceptor.ContextInterceptor;
import top.lrshuai.common.security.interceptor.LoginInterceptor;

/**
 * 接口拦截
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** 不需要拦截地址 */
    public static final String[] excludeUrls = { "/login" };

    @Bean
    public ContextInterceptor contextInterceptor(){
        return new ContextInterceptor();
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加登录拦截
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls);
        // 添加上下文参数拦截
        registry.addInterceptor(contextInterceptor())
                .addPathPatterns("/**");
    }
}
