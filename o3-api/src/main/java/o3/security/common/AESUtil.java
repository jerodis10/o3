package o3.security.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
public class AESUtil {

    public static String alg = "AES/CBC/PKCS5Padding";
    private static String key = "abcdefghabcdefghabcdefghabcdefgh"; // 32byte
    private static String iv = "0123456789abcdef"; // 16byte

    // 암호화
    public static String encrypt(String text) {

        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Error while encrypt: " + e);
            return null;
        }

    }

    // 복호화
    public static String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            log.error("Error while encrypt: " + e);
            return null;
        }
    }


//    private byte[] key;

//    public static String encrypt(String str) {
//        try {
//            //알고리즘/블럭 암호화 방식/Padding방식(메세지 길이가 짧은 경우 어떻게 처리 할 것인가?)
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
////            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//            SecretKeySpec keySpec = new SecretKeySpec("abcdefghabcdefghabcdefghabcdefgh".getBytes(), "AES");
//            IvParameterSpec ivParamSpec = new IvParameterSpec("0123456789abcdef".getBytes());
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
//            //cipher를 통한 암호화 결과 타입은 byte array이다. 이를 쉽게 다루기위해 base64 string 으로 encoding하여 사용한다.
//            return encodeBase64(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
//        } catch (Exception e) {
//            log.error("Error while encrypt: " + e);
//            return null;
//        }
//    }
//
//    public static String decrypt(String str) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//
//            SecretKeySpec keySpec = new SecretKeySpec("abcdefghabcdefghabcdefghabcdefgh".getBytes(), "AES");
//            IvParameterSpec ivParamSpec = new IvParameterSpec("0123456789abcdef".getBytes());
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
//
////            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
//
//            return new String(cipher.doFinal(decodeBase64(str)));
//        } catch (Exception e) {
//            log.error("Error while decrypt: " + e);
//            return null;
//        }
//    }
//
//    private static String encodeBase64(byte[] source) {
//        return Base64.getEncoder().encodeToString(source);
//    }
//
//    private static byte[] decodeBase64(String encodedString) {
//        return Base64.getDecoder().decode(encodedString);
//    }
}
