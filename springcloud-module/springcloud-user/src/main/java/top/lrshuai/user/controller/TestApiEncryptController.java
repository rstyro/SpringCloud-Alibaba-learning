package top.lrshuai.user.controller;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.encrypt.annotation.DecodeRequestBody;
import top.lrshuai.common.encrypt.annotation.EncodeResponseBody;
import top.lrshuai.common.encrypt.annotation.EncryptCombine;
import top.lrshuai.common.encrypt.config.EncryptProperties;
import top.lrshuai.user.api.dto.UpdateAccountDto;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "apiEncrypt",description = "测试Api接口加密")
@RestController
@RequestMapping("/apiEncrypt")
public class TestApiEncryptController {


    public static void main(String[] args) {
        // 后端公钥
        String pk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOlRMYBZKw8OY/3Lij5ckVOzWQxSBsoixQYBHkTJwnDZqJC0j0pNkxkQRGOekzt1QztSR7769RgAOwPecXtlIH9ksw4AK+LRe3ntSYvoThxr+2U9+4j9hI1/g+CPOA/kdOQLYxygW3hwoFKG2iiWSXWvgso9AGZFZKjHxP0ZWMvwIDAQAB";
        String aesKey = EncryptProperties.generateAesSecret(256);
        String iv="123456789abcdefh";
        Map<String,Object> data = new HashMap<>();
        data.put("userId",110);
        data.put("amount",999999);
        data.put("orderNumber","168");
        String dataStr = JSON.toJSONString(data);
        RSA rsa = SecureUtil.rsa(null, pk);
        AES aes = new AES(Mode.valueOf("CBC")
                , Padding.valueOf("PKCS5Padding")
                , aesKey.getBytes(StandardCharsets.UTF_8)
                , StringUtils.hasText(iv) ? iv.getBytes(StandardCharsets.UTF_8) : null);
        System.out.println("原key="+aesKey);
        System.out.println("参数header:Key="+rsa.encryptBase64(aesKey, KeyType.PublicKey));
        System.out.println("参数请求body="+aes.encryptBase64(dataStr));
    }


    @PostMapping("/test1")
    @ResponseBody
    @EncryptCombine
    public Object test1(@RequestBody(required = false) UpdateAccountDto dto){
        System.out.println("dto="+dto);
        return R.ok(dto);
    }

    @PostMapping("/test2")
    @ResponseBody
    @EncodeResponseBody
    public Object test2(@RequestBody(required = false) UpdateAccountDto dto){
        System.out.println("dto="+dto);
        return R.ok(dto);
    }

    @PostMapping("/test3")
    @ResponseBody
    @DecodeRequestBody
    public Object test3(@RequestBody(required = false) UpdateAccountDto dto){
        System.out.println("dto="+dto);
        return R.ok(dto);
    }


}
