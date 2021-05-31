package com.udacity.jwdnd.course1.cloudstorage.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HashService {

  private final Logger logger = LoggerFactory.getLogger(HashService.class);

  public String getHashedValue(String data, String salt) {
    byte[] hashedValue = null;

    KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), 5000, 128);
    try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      hashedValue = factory.generateSecret(spec).getEncoded();
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      logger.error(e.getMessage());
    }

    return Base64.getEncoder().encodeToString(hashedValue);
  }

  public String generateRandomKey() {
    byte[] key = new byte[16];
    SecureRandom random = new SecureRandom();
    random.nextBytes(key);
    return Base64.getEncoder().encodeToString(key);
  }
}
