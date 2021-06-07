package top.lrshuai.nacos.commons;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RemoteResult<T> implements Serializable {
    private String status;
    private String message;
    private String module="default";
    private T data;

    public static <T> RemoteResult<T> success(){
        return new RemoteResult<T>().setStatus("200").setMessage("success");
    }

    public Boolean ok(){
        return "200".equals(getStatus());
    }

    public static <T> RemoteResult<T> data(T t){
        RemoteResult<T> success = success();
        return success.setData(t);
    }

    public static <T> RemoteResult<T> error(ApiResultEnum apiResultEnum){
        return new RemoteResult<T>().setStatus(apiResultEnum.getStatus()).setMessage(apiResultEnum.getMessage());
    }

    public static <T> RemoteResult<T> error(){
        return new RemoteResult<T>().setStatus("500").setMessage("远程服务繁忙");
    }

}
