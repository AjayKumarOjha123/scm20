package com.scm.scm20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.helper.ResourceNotFoundException;
import com.scm.scm20.repository.UserRepository;
import com.scm.scm20.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
user.setRoleList(List.of(AppConstants.ROLE_USER));



        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        User save = userRepository.save(user2);
        return Optional.ofNullable(save);
      }

    @Override
    public void deleteUserById(String id) {
        User user2 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public boolean isUserExistById(String id) {
        User user2 = userRepository.findById(id).orElse(null);
        return user2 != null ? true : false;
    }
    
    @Override
    public boolean isUserExistByEmail(String email) {
        User user2 = userRepository.findByEmail(email).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
      return  userRepository.findByEmail(email).orElseThrow(null);
    }

}
