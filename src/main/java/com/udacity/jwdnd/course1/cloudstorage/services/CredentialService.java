package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        String encodedSalt = generateSalt();
        credential.setKei(encodedSalt);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);
        credential.setPassword(encryptedPassword);
        return credentialMapper.insert(credential);
    }

    public int updateCredential(Credential credential) {
        String encodedSalt = generateSalt();
        credential.setKei(encodedSalt);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);
        credential.setPassword(encryptedPassword);
        return credentialMapper.update(credential);
    }

    public int deleteCredential(int credentialId, int userId) {
        return credentialMapper.delete(credentialId, userId);
    }

    public List<Credential> getUserCredentials(int userId) {
        List<Credential> credentialList = credentialMapper.getCredentials(userId);
        return credentialList.stream()
                .map(credential -> wrapCredential(credential))
                .collect(Collectors.toList());
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private Credential wrapCredential(Credential c) {
        Credential credential = c;
        credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKei()));
        return credential;
    }
}
