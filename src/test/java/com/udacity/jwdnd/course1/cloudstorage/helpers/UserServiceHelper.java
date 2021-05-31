package com.udacity.jwdnd.course1.cloudstorage.helpers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserServiceHelper {
  private UserService userService;

  public UserServiceHelper(UserService userService) {
    this.userService = userService;
  }

  public User createTestUser() {
    String username = ("automated-user-" + UUID.randomUUID()).substring(0, 20);
    String password = "super-secret";

    User user = new User(null, username, null, password, "Automated", "User");

    this.userService.createUser(user);

    return user;
  }
}
