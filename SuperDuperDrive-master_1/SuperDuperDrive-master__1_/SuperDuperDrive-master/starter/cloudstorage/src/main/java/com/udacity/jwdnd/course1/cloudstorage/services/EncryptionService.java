package com.udacity.jwdnd.course1.cloudstorage.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Service
public class EncryptionService {
    private final Logger encryptionLogger = LoggerFactory.getLogger(EncryptionService.class);
    public String decryptDataUsingKey(String data, String key) {
        byte[] decryptedDataByKey = null;

        try {
            Cipher cipherAESKey = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretAESKey = new SecretKeySpec(key.getBytes(), "AES");
            cipherAESKey.init(Cipher.DECRYPT_MODE, secretAESKey);
            decryptedDataByKey = cipherAESKey.doFinal(Base64.getDecoder().decode(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            encryptionLogger.error(e.getMessage());
        }

        return new String(decryptedDataByKey);
    }

    public String encryptDataUsingKey(String data, String key) {
        byte[] encryptedDataByKey = null;

        try {
            Cipher cipherAESKey = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretAESKey = new SecretKeySpec(key.getBytes(), "AES");
            cipherAESKey.init(Cipher.ENCRYPT_MODE, secretAESKey);
            encryptedDataByKey = cipherAESKey.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException badPaddingException) {
            encryptionLogger.error(badPaddingException.getMessage());
        }
        assert encryptedDataByKey != null;
        return Base64.getEncoder().encodeToString(encryptedDataByKey);
    }
}
