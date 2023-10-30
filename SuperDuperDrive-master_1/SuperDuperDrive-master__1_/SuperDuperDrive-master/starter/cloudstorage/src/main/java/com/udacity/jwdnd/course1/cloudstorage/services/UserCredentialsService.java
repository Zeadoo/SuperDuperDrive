package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class UserCredentialsService {
    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public UserCredentialsService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }


    public void addCredential(Credential userCredential, int currentUserId) {
        userCredential.setKey(generateNewKey());
        userCredential.setPassword(encryptionService.encryptDataUsingKey(userCredential.getPassword(), userCredential.getKey()));
        userCredential.setUserId(currentUserId);

        credentialMapper.insertCredentials(userCredential);
    }

    public void updateCredential(Credential credential) {

        credential.setKey(generateNewKey());
        credential.setPassword(encryptionService.encryptDataUsingKey(credential.getPassword(), credential.getKey()));
        credentialMapper.updateCredential(credential);
    }

    private String generateNewKey() {

        final String ALL_CHARACTER = "abcdefghijklmnopqrstuvwxyz0123456789";

        int credentialKeyLength = 16;
        SecureRandom secureRandom = new SecureRandom();
        StringBuffer stringBuffer = new StringBuffer(credentialKeyLength);
        for (int i = 0; i < credentialKeyLength; i++) {
            int nextIntOffset = secureRandom.nextInt(ALL_CHARACTER.length());
            stringBuffer.append(ALL_CHARACTER.charAt(nextIntOffset));
        }
        return stringBuffer.toString();
    }
    public Credential getCredentialUsingId(Integer credentialId) {
        return credentialMapper.getCredentials(credentialId);
    }

    public void deleteCurrentCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
    public List<Credential> getCredentialaAsList(Integer userId) {

        List<Credential> allCredentials = credentialMapper.getAllCredentials(userId);

        for (Credential credential : allCredentials) {
            credential.setPassword(encryptionService.decryptDataUsingKey(credential.getPassword(), credential.getKey()));
        }

        return allCredentials;
    }

}
