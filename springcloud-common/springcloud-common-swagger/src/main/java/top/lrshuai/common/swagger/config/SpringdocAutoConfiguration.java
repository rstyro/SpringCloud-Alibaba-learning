package top.lrshuai.common.swagger.config;

import cn.hutool.core.net.NetUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import top.lrshuai.common.core.constant.SecurityConst;
import top.lrshuai.common.core.factory.YmlPropertySourceFactory;

import javax.annotation.Resource;


/**
 * swagger-ui 配置
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
@Import({SpringdocProperties.class})
@PropertySource(value = "classpath:swagger-config.yml", factory = YmlPropertySourceFactory.class)
public class SpringdocAutoConfiguration {

    @Resource
    SpringdocProperties properties;

    @Value("${server.port}")
    private int port;

    @Bean
    public OpenAPI openAPI() {
        //配置认证、请求头参数
        Components components = new Components();
        components.addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                .addParameters(SecurityConst.USER_ID, new Parameter().in("header").schema(new StringSchema()).name(SecurityConst.USER_ID))
                .addParameters(SecurityConst.TOKEN, new Parameter().in("header").schema(new StringSchema()).name(SecurityConst.TOKEN))
                .addParameters(SecurityConst.PAGE_NO, new Parameter().in("header").schema(new StringSchema()).name(SecurityConst.PAGE_NO))
                .addParameters(SecurityConst.PAGE_SIZE, new Parameter().in("header").schema(new StringSchema()).name(SecurityConst.PAGE_SIZE))
        ;
        log.info("已启用swagger-ui,访问地址：http://{}:{}/swagger-ui.html", NetUtil.getLocalhostStr(),port);
        return new OpenAPI().info(apiInfo()).components(components);
    }


    /**
     * 添加全局的请求头参数
     */
    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/" + SecurityConst.USER_ID));
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/" + SecurityConst.TOKEN));
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/" + SecurityConst.PAGE_NO));
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/" + SecurityConst.PAGE_SIZE));
                });
    }

    /**
     * 添加文档标题等信息
     */
    private Info apiInfo() {
        return new Info()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(apiContact())
                .license(license())
                .termsOfService(properties.getTermsOfServiceUrl());
    }

    /**
     * 许可
     */
    private License license() {
        return new License()
                .name(properties.getLicense())
                .url(properties.getLicenseUrl());
    }


    /**
     * 联系人信息
     */
    private Contact apiContact() {
        return new Contact()
                .name(properties.getName())
                .url(properties.getAuthUrl())
                .email(properties.getEmail());
    }

}
