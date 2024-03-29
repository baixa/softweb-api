package com.softweb.api.store.services;

import com.softweb.api.store.model.dto.user.AbstractUserSaveDto;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.model.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById (String userId) {
        return userRepository.findById(Long.valueOf(userId)).orElse(null);
    }

    public User saveUser(AbstractUserSaveDto userDto) {
        User requestedUser = new User(userDto);
        hashPassword(requestedUser);
        return userRepository.save(requestedUser);
    }

    private void hashPassword(User requestedUser) {
        requestedUser.setPassword(passwordEncoder.encode(requestedUser.getPassword()));
    }

    public void deleteUserById (String userId) {
        userRepository.deleteById(Long.valueOf(userId));
    }

    public Optional<User> getUserByUsername(String authenticationName) {
        return userRepository.findByUsername(authenticationName);
    }

    public List<User> getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.getContent();
    }

    public void authUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty())
            return;

        User user = optionalUser.get();
        user.setLastEntered(LocalDateTime.now());
        userRepository.save(user);
    }
}
