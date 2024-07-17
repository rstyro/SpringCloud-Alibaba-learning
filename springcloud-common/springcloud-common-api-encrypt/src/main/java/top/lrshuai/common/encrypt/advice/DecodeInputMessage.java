package top.lrshuai.common.encrypt.advice;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.exception.ServiceException;
import top.lrshuai.common.encrypt.config.EncryptProperties;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 解码处理
 * @author rstyro
 */
@Slf4j
public class DecodeInputMessage implements HttpInputMessage {

    private HttpHeaders headers;

    private InputStream body;

    public DecodeInputMessage(HttpInputMessage httpInputMessage, EncryptProperties encryptProperties) {
        // 这里是body 读取之前的处理
        this.headers = httpInputMessage.getHeaders();
        String encodeAesKey = "";
        List<String> keys = this.headers.get(encryptProperties.getAesProperties().getKeyName());
        if (ObjectUtils.isEmpty(keys)) {
            throw new ServiceException(ApiResultEnum.ERROR_ENCRYPT_KEY_IS_NULL);
        }
        encodeAesKey = keys.get(0);
        try {
            EncryptProperties.RsaProperties rsaProperties = encryptProperties.getRsaProperties();
            // 1、解码得到aes 密钥
            String aesKey = SecureUtil.rsa(rsaProperties.getPrivateKey(), rsaProperties.getPublicKey()).decryptStr(encodeAesKey, KeyType.PrivateKey);
            // 2、从inputStreamReader 得到aes 加密的内容
            String aesEncodeContent = new BufferedReader(new InputStreamReader(httpInputMessage.getBody())).lines().collect(Collectors.joining(System.lineSeparator()));
            // 3、AES通过密钥 解码
            AES aes = encryptProperties.getAes(aesKey);
            String data = aes.decryptStr(aesEncodeContent);
            if (!StringUtils.isEmpty(data)) {
                // 4、重新写入到controller
                this.body = new ByteArrayInputStream(data.getBytes());
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(ApiResultEnum.ERROR_REQUEST_DECODE_FAIL);
        }
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }


}
