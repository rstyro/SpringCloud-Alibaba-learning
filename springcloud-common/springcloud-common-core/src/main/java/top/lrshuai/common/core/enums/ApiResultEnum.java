package top.lrshuai.common.core.enums;

public enum ApiResultEnum {

	SUCCESS(200,"ok"),
	FAILED(400,"请求失败"),
	NO_AUTH(403,"您无权限访问"),
	BLOCKED_FLOW(429,"请求超过最大数，请稍候再试"),
	BLOCKED_DEGRADE(430,"请求被降级，请稍候再试"),
	BLOCKED_AUTHORITY(431,"请求未授权"),
	ERROR(500,"服务繁忙，请稍后重试"),
	ERROR_NULL(501,"空指针异常"),
	ERROR_CLASS_CAST(502,"类型转换异常"),
	ERROR_RUN(503,"运行时异常"),
	ERROR_IO(504,"上传文件异常"),
	ERROR_REQUEST_ERR(505,"请求方法错误"),
	ERROR_REQUEST_DECODE_FAIL(506,"参数解密失败"),
	ERROR_ENCRYPT_KEY_IS_NULL(507,"约定的密钥KEY为空"),

	/**系统框架，报错code:10000-20000 */
	SYSTEM_CODE_ERROR(10000,"验证码错误"),
	SYSTEM_ACCOUNT_NOT_FOUND(10001,"账号或密码错误"),
	SYSTEM_PASSWORD_ERROR(10002,"账号或密码错误"),
	SYSTEM_USER_NOT_FOUND(10003,"用户找不到"),
	SYSTEM_USER_EXIST(10004,"用户名已存在"),
	SYSTEM_USER_ABOVE_MAX_RETRY_COUNT(10005,"用户登录错误次数过多已被锁定，请稍后再试"),
	SYSTEM_REPEAT_SUBMIT_TIME_LIMIT(10006,"重复提交间隔时间不能小于'1'秒"),
	SYSTEM_REPEAT_SUBMIT(10007,"请求重复提交，请稍后重试"),


	ACCOUNT_INSUFFICIENT_BALANCE(20001,"余额不足"),
	ACCOUNT_UPDATE_ERROR(20002,"更新账户失败"),
	STORAGE_INSUFFICIENT_COUNT(20003,"库存不足"),
	FEIGN_ERROR(20004,"远程服务繁忙"),
	TOKEN_USER_INVALID(20005,"Token过期或用户未登录"),
	MALL_GOODS_NOT_FOUND(20006,"找不到商品编号"),
	;
	
	private String message;
	private int code;
	
	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	private ApiResultEnum(int code, String message) {
		this.message = message;
		this.code = code;
	}

	
}
