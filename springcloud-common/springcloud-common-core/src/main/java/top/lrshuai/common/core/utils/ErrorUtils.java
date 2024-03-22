package top.lrshuai.common.core.utils;

import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.exception.ServiceException;

public class ErrorUtils {

    public static void err(ApiResultEnum apiResultEnum) {
        throw new ServiceException(apiResultEnum.getMessage(), apiResultEnum.getCode());
    }

    public static void err(ApiResultEnum apiResultEnum, String... params) {
        String resultMsg = apiResultEnum.getMessage();
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                resultMsg = resultMsg.replaceAll("\\{" + i + "\\}", params[i]);
            }
        }
        throw new RuntimeException(resultMsg);
    }
}
