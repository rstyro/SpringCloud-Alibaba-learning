package top.lrshuai.nacos.commons.utils;

import java.util.UUID;

public class IdUtil {

    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
