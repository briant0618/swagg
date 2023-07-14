package com.example.swagger.service;


import com.example.swagger.entity.UserEntity;
import com.example.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User 등록
    public UserEntity registerUser(String username, String password, String email, UserEntity.RoleType role) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));

        return userRepository.save(user);
    }

    // Login 구현
    public UserEntity loginUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    // User 조회
    public UserEntity getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // User Update
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}