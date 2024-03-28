package top.lrshuai.common.redis.config.enums;

public enum RedisKeyEnum {
    //登录Token
    LOGIN_TOKEN("cloud:user:login_token:", 3600 * 8, "登录Token"),
    REPEAT_SUBMIT("cloud:security:repeat_submit:", 5000, "请求重复提交"),


    ;

    private final String key;
    /**
     * 过期时间，单位：毫秒
     */
    private final long expireTime;
    private final String message;

    RedisKeyEnum(String key, int expireTime, String message) {
        this.key = key;
        this.expireTime = expireTime;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public String getMessage() {
        return message;
    }

}
