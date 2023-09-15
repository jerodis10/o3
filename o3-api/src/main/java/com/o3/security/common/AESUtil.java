package com.o3.security.common;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
@UtilityClass
public class AESUtil {

    private String alg = "AES/CBC/PKCS5Padding";
//    private String key = "oingisprettyinth"; // 32byte
    private String key = "abcdefghabcdefghabcdefghabcdefgh"; // 32byte
//    private String iv = "0123456789abcdef0123456789abcdef"; // 16byte
    private String iv = "0123456789abcdef"; // 16byte

    // 암호화
    public String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Error while encrypt: " + e);
            return null;
        }

    }

    // 복호화
    public String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

//            byte[] decodedBytes = Base64Utils.decodeFromUrlSafeString(cipherText);
//            byte[] decodedBytes = Base64Utils.decodeUrlSafe(cipherText.getBytes());
            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
//            return Base64.getEncoder().encodeToString(new String(decrypted, StandardCharsets.UTF_8));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Error while encrypt: " + e);
            return null;
        }
    }

}