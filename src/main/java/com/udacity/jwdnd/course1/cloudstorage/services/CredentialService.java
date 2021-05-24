package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
  public final UserService userService;
  public final CredentialMapper credentialMapper;
  public final EncryptionService encryptionService;

  public CredentialService(
      UserService userService,
      CredentialMapper credentialMapper,
      EncryptionService encryptionService) {
    this.userService = userService;
    this.credentialMapper = credentialMapper;
    this.encryptionService = encryptionService;
  }

  public Credential getCredential(Credential credential) {
    credential.setUserId(this.userService.getCurrentUser().getUserId());
    return this.credentialMapper.getCredential(credential);
  }

  public List<Credential> getCredentialsForCurrentUser() {
    return this.getCredentialsForUserId(this.userService.getCurrentUser().getUserId());
  }

  public List<Credential> getCredentialsForUserId(Integer userId) {
    return this.credentialMapper.getCredentialsForUserId(userId).stream()
        .map(credential -> addPlainTextPassword(credential))
        .collect(Collectors.toList());
  }

  public Integer createOrUpdateCredential(Credential credential) {
    credential.setUserId(this.userService.getCurrentUser().getUserId());

    credential.setKey(generateKey());
    credential.setPassword(
        this.encryptionService.encryptValue(credential.getPassword(), credential.getKey()));

    if (credential.getCredentialId() == null) {
      return this.credentialMapper.insert(credential);
    } else {
      return this.credentialMapper.update(credential);
    }
  }

  public void deleteCredentialByCredentialId(Integer credentialId) {
    Integer userId = this.userService.getCurrentUser().getUserId();
    this.credentialMapper.deleteCredentialByCredentialId(credentialId, userId);
  }

  protected Credential addPlainTextPassword(Credential credential) {
    credential.setPlainTextPassword(
        this.encryptionService.decryptValue(credential.getPassword(), credential.getKey()));

    return credential;
  }

  protected String generateKey() {
    byte[] key = new byte[16];
    SecureRandom random = new SecureRandom();
    random.nextBytes(key);
    return Base64.getEncoder().encodeToString(key);
  }
}
