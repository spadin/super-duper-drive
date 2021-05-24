package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserMapper userMapper;
  private final HashService hashService;

  public UserService(UserMapper userMapper, HashService hashService) {
    this.userMapper = userMapper;
    this.hashService = hashService;
  }

  public boolean isUsernameAvailable(String username) {
    return this.userMapper.getUser(username) == null;
  }

  public Integer createUser(User user) {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    String encodedSalt = Base64.getEncoder().encodeToString(salt);
    String hashedPassword = this.hashService.getHashedValue(user.getPassword(), encodedSalt);
    return this.userMapper.insert(
        new User(
            null,
            user.getUsername(),
            encodedSalt,
            hashedPassword,
            user.getFirstName(),
            user.getLastName()));
  }

  public User getUser(String username) {
    return this.userMapper.getUser(username);
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = (String) authentication.getPrincipal();
    return this.getUser(username);
  }
}
