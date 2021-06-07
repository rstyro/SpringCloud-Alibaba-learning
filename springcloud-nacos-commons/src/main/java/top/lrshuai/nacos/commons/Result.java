package top.lrshuai.nacos.commons;

import java.util.HashMap;
import java.util.Map;


public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String DATA = "DATA";

	public Result() {
		put(STATUS, 200);
		put(MESSAGE, "ok");
	}

	public static Result error() {
		return error("500", "系统错误，请联系管理员");
	}

	public static Result error(String msg) {
		return error("500", msg);
	}

	public static Result error(String status, String msg) {
		Result r = new Result();
		r.put(STATUS, status);
		r.put(MESSAGE, msg);
		return r;
	}

	public static Result error(ApiResultEnum resultEnum) {
		Result r = new Result();
		r.put(STATUS, resultEnum.getStatus());
		r.put(MESSAGE, resultEnum.getMessage());
		return r;
	}

	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	public static Result ok(Object data) {
		Result r = new Result();
		r.put(DATA,data);
		return r;
	}

	public static Result ok() {
		return new Result();
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}