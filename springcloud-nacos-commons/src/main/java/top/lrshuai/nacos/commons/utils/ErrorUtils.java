package top.lrshuai.nacos.commons.utils;

import top.lrshuai.nacos.commons.ApiResultEnum;

public class ErrorUtils {
    public static void err(ApiResultEnum apiResultEnum){
        throw new RuntimeException(apiResultEnum.getMessage());
    }

    public static void err(ApiResultEnum apiResultEnum,String... params){
        String resultMsg = apiResultEnum.getMessage();
        if(params!=null&&params.length>0){
            for (int i = 0; i < params.length; i++) {
                resultMsg = resultMsg.replaceAll("\\{" + i + "\\}", params[i]);
            }
        }
        throw new RuntimeException(resultMsg);
    }
}
