package top.lrshuai.common.encrypt.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.UUID;

@Data
@ConfigurationProperties(prefix = "api.encrypt")
public class EncryptProperties {

    private boolean enable;

    private RsaProperties rsaProperties;

    private AesProperties aesProperties;

    public AES getAes(String aesKey){
        return  new AES(Mode.valueOf(aesProperties.getMode())
                ,Padding.valueOf(aesProperties.getPadding())
                ,aesKey.getBytes(StandardCharsets.UTF_8)
                ,StringUtils.hasText(aesProperties.getIv())? aesProperties.getIv().getBytes(StandardCharsets.UTF_8):null);
    }

    @Data
    @NoArgsConstructor
    public static class RsaProperties {
        /**
         * 后端 RSA 公钥
         */
        private String publicKey;
        /**
         * 后端 RSA 私钥
         */
        private String privateKey;

        /**
         * 前端 RSA 公钥
         */
        private String webPublicKey;
    }


    @Data
    @NoArgsConstructor
    public static class AesProperties {

        /**
         * AES 参数名
         */
        private String keyName;
        private String mode= Mode.CBC.name();
        private String padding= Padding.PKCS5Padding.name();
        /**
         * AES 向量,支持的模式：CBC、CFB、OFB、CTR
         */
        private String iv;
    }

    /**
     * 生成 随机向量
     * @return 128位的向量
     */
    public static String generateIv() {
        //向量长度固定为128位（16字节）
        return generateAesSecret(128);
    }

    /**
     * 随便生成AES的密钥
     * @param keySize 密钥大小，128、129、256 位数
     * @return AES的密钥
     */
    public static String generateAesSecret(int keySize) {
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new IllegalArgumentException("密钥长度错误，仅支持128、192、256位");
        }
        String uuidWithoutDashes = UUID.randomUUID().toString().replace("-", "");
        // 因为一个字节等于8位，所以直接计算所需字节数
        int length = keySize / 8;
        return uuidWithoutDashes.substring(0, length);
    }


    /**
     * 随便生成RSA的密钥
     */
    public static KeyPair generateRsaSecret() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        // base64编码的 私钥
        String privateBaseKey = Base64.encode(pair.getPrivate().getEncoded());
        // base64编码的 公钥钥
        String publicBaseKey = Base64.encode(pair.getPublic().getEncoded());
        System.out.println("私钥="+privateBaseKey);
        System.out.println("公钥="+publicBaseKey);
        return pair;
    }



}
