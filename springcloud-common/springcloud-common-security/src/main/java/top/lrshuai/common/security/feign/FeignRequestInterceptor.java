package top.lrshuai.common.security.feign;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import top.lrshuai.common.core.constant.SecurityConst;
import top.lrshuai.common.core.utils.IpUtils;
import top.lrshuai.common.core.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * feign 请求拦截器
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (ObjUtil.isNotNull(httpServletRequest)) {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            String token = headers.get(SecurityConst.TOKEN);
            if (StrUtil.isNotEmpty(token)) {
                requestTemplate.header(SecurityConst.TOKEN, token);
            }
            String uid = headers.get(SecurityConst.USER_ID);
            if (StrUtil.isNotEmpty(uid)) {
                requestTemplate.header(SecurityConst.USER_ID, uid);
            }
            String pageNo = headers.get(SecurityConst.PAGE_NO);
            if (StrUtil.isNotEmpty(uid)) {
                requestTemplate.header(SecurityConst.PAGE_NO, pageNo);
            }
            String pageSize = headers.get(SecurityConst.PAGE_SIZE);
            if (StrUtil.isNotEmpty(pageSize)) {
                requestTemplate.header(SecurityConst.PAGE_SIZE, pageSize);
            }
            String trackerId = headers.get(SecurityConst.TRACKER_ID);
            if (StrUtil.isNotEmpty(trackerId)) {
                requestTemplate.header(SecurityConst.TRACKER_ID, trackerId);
            }
            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr(ServletUtils.getRequest()));
        }
    }
}