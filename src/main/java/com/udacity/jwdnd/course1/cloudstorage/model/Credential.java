package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {

  private final Integer credentialId;
  private final String url;
  private final String username;
  private final String key;
  private final String password;
  private final Integer userId;

  public Credential(
      Integer credentialId,
      String url,
      String username,
      String key,
      String password,
      Integer userId) {
    this.credentialId = credentialId;
    this.url = url;
    this.username = username;
    this.key = key;
    this.password = password;
    this.userId = userId;
  }
}
