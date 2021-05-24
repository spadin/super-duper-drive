package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
  public final UserService userService;
  public final CredentialMapper credentialMapper;

  public CredentialService(UserService userService, CredentialMapper credentialMapper) {
    this.userService = userService;
    this.credentialMapper = credentialMapper;
  }

  public List<Credential> getCredentialsForCurrentUser() {
    return this.getCredentialsForUserId(this.userService.getCurrentUser().getUserId());
  }

  public List<Credential> getCredentialsForUserId(Integer userId) {
    return this.credentialMapper.getCredentialsForUserId(userId);
  }

  public Integer createOrUpdateCredential(Credential credential) {
    credential.setUserId(this.userService.getCurrentUser().getUserId());

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
}
