package top.lrshuai.nacos.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lrshuai.nacos.commons.consts.Consts;

import javax.servlet.http.HttpServletRequest;

/**
 * feign调用,添加token
 */
@Component
public class FeignTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (null == getHttpServletRequest()){
            return;
        }
        requestTemplate.header(Consts.TOKEN,getRequestToken(getHttpServletRequest()));
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        // 从header中获取token
        String token = httpRequest.getHeader(Consts.TOKEN);
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter(Consts.TOKEN);
        }
        return token;
    }

    private HttpServletRequest getHttpServletRequest(){
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (Exception e){
            return null;
        }
    }
}
