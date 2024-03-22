package top.lrshuai.common.core.context;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import top.lrshuai.common.core.constant.SecurityConst;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取当前线程变量中的 用户id、用户名称、Token等信息
 * 注意： 必须在网关通过请求头的方法传入，同时在HeaderInterceptor拦截器设置值。 否则这里无法获取
 * @author ruoyi
 */
public class SecurityContextHolder {

    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = getLocalMap();
        map.put(key, value == null ? StrUtil.EMPTY : value);
    }

    public static String get(String key) {
        Map<String, Object> map = getLocalMap();
        return Convert.toStr(map.getOrDefault(key, StrUtil.EMPTY));
    }

    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> map = getLocalMap();
        return clazz.cast(map.getOrDefault(key, null));
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<String, Object>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setLocalMap(Map<String, Object> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }

    public static Long getUserId() {
        return Convert.toLong(get(SecurityConst.USER_ID), 0L);
    }

    public static void setUserId(String account) {
        set(SecurityConst.USER_ID, account);
    }

    public static void setPageNo(Integer pageStart) {
        set(SecurityConst.PAGE_NO, pageStart);
    }

    public static int getPageNo() {
        return Optional.ofNullable(get(SecurityConst.PAGE_NO, Integer.class)).orElse(1);
    }

    public static void setPageSize(Integer pageSize) {
        set(SecurityConst.PAGE_SIZE, pageSize);
    }

    public static int getPageSize() {
        return Optional.ofNullable(get(SecurityConst.PAGE_SIZE, Integer.class)).orElse(10);
    }

    public static void setTrackerId(String trackerId) {
        if (StrUtil.isNotEmpty(trackerId)) {
            set(SecurityConst.TRACKER_ID, trackerId);
        } else {
            set(SecurityConst.TRACKER_ID, UUID.fastUUID().toString(true));
        }
    }

    public static String getToken() {
        return get(SecurityConst.TOKEN);
    }

    public static void setToken(String username) {
        set(SecurityConst.TOKEN, username);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
