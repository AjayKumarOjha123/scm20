package com.scm.scm20.services;

import com.scm.scm20.entities.User;
import java.util.*;


public interface UserServices {

    User saveUser(User user);

   Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUserById(String id);

    boolean isUserExistById(String id);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

    User getUserByEmail(String email);
}
