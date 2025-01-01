package com.github.howwrite.treasure.security.helper;

import com.github.howwrite.treasure.security.config.SecurityKeyConfig;
import com.github.howwrite.treasure.security.config.SecuritySwitch;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DecryptionHelper {

    private static final Map<String, Cipher> cipherMap = new ConcurrentHashMap<>();

    private static Cipher getCipher(String key, String ivKey) {
        String cipherKey = key + "_" + ivKey;
        Cipher cipher = cipherMap.get(cipherKey);
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes(StandardCharsets.UTF_8));
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                cipherMap.put(cipherKey, cipher);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return cipher;
    }

    private static int sumAsciiValues(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += c;
        }
        return sum;
    }

    private static Cipher getCipher(Map<String, String> keyMap) {
        List<String> keySortesList = keyMap.keySet().stream().sorted(Comparator.comparingInt(DecryptionHelper::sumAsciiValues)).toList();
        return getCipher(keyMap.get(keySortesList.get(1)), keyMap.get(keySortesList.get(keySortesList.size() - 2)));
    }


    public static String decrypt(String encryptedData) throws Exception {
        // 获取密钥
        SecurityKeyConfig securityKeyConfig = SecuritySwitch.aesKeyConfig.calValue();
        Cipher cipher = getCipher(securityKeyConfig.getNow());

        byte[] encryptedBytes = Base64.decodeBase64(encryptedData);
        byte[] original = cipher.doFinal(encryptedBytes);
        return new String(original);
    }
}
