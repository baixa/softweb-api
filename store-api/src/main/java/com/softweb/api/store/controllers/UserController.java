package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.user.UserAdminDto;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById (@PathVariable(name = "id") String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(new UserAdminDto(user));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers () {
        List<User> userList = userService.getUsers();
        List<UserAdminDto> userAdminDtos = userList.stream().map(UserAdminDto::new).toList();
        return ResponseEntity.ok(userAdminDtos);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postUser (@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> putUser (@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser (@PathVariable(name = "id") String userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
