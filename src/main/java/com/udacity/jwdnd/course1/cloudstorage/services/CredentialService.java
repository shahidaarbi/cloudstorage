package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
    	this.credentialMapper = credentialMapper;
    	this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential, Integer userId) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        
    	if(null==credential.getCredentialid()) {
    		return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userId));
    	}
    	return credentialMapper.update(credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getCredentialid());
    }
    
    public void deleteCredential(Integer noteid) {
    	credentialMapper.delete(noteid);
    }

    public List<Credential> getCredentials(Integer userId) {
    	List<Credential> credentials = credentialMapper.selectCredentialByUserId(userId);
    	
    	for(Credential credential: credentials) {
    		String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    		credential.setPassword(decryptedPassword);    		
    	}
        return credentials;
    }
    
    public Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    
    
}
