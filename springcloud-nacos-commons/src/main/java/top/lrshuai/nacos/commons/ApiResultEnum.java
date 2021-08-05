package top.lrshuai.nacos.commons;

public enum ApiResultEnum {
	SUCCESS("200","ok"),
	FAILED("400","请求失败"),
	AUTH_ERR("403","权限不足"),
	ERROR("500","服务繁忙"),

	ACCOUNT_INSUFFICIENT_BALANCE("10000","余额不足"),
	ACCOUNT_UPDATE_ERROR("10001","更新账户失败"),
	STORAGE_INSUFFICIENT_COUNT("20000","库存不足"),
	FEIGN_ERROR("30000","远程服务繁忙"),
	TOKEN_USER_INVALID("70000","Token过期或用户未登录"),
	;
	
	private String message;
	private String status;
	
	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}
	private ApiResultEnum(String status,String message) {
		this.message = message;
		this.status = status;
	}

	
}
