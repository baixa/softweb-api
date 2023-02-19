package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById (String userId) {
        return userRepository.findById(Long.valueOf(userId)).orElse(null);
    }

    public List<User> getUsers () {
        return userRepository.findAll();
    }

    public void saveUser (User user) {
        userRepository.save(user);
    }

    public void deleteUser (User user) {
        userRepository.delete(user);
    }

    public void deleteUserById (String userId) {
        userRepository.deleteById(Long.valueOf(userId));
    }
}
