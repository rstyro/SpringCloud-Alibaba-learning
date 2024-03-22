package top.lrshuai.common.security.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import top.lrshuai.common.core.constant.SecurityConst;
import top.lrshuai.common.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 上下文参数注入
 */
public class ContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pageNo = getParams(SecurityConst.PAGE_NO,request);
        String pageSize = getParams(SecurityConst.PAGE_SIZE,request);
        String trackerId = getParams(SecurityConst.TRACKER_ID,request);
        String userId = getParams(SecurityConst.USER_ID,request);
        String token = getParams(SecurityConst.TOKEN,request);
        SecurityContextHolder.setTrackerId(StringUtils.hasLength(trackerId)?trackerId:IdUtil.fastSimpleUUID());
        SecurityContextHolder.setPageNo(StringUtils.hasLength(pageNo)&&StrUtil.isNumeric(pageNo)?Integer.parseInt(pageNo):1);
        SecurityContextHolder.setPageSize(StringUtils.hasLength(pageSize)&&StrUtil.isNumeric(pageSize)?Integer.parseInt(pageSize):10);
        SecurityContextHolder.setUserId(userId);
        SecurityContextHolder.setToken(token);
        // todo 可以通过获取token,通过token获取用户信息，存用户信息
        return true;
    }

    public String getParams(String paramKey,HttpServletRequest request){
        return Optional.ofNullable(request.getHeader(paramKey)).orElse(request.getParameter(paramKey));
    }

}
