package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.user.*;
import com.softweb.api.store.model.entities.Authorities;
import com.softweb.api.store.model.entities.Authority;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.services.AuthenticationService;
import com.softweb.api.store.services.AuthorityService;
import com.softweb.api.store.services.UserService;
import com.softweb.api.store.utils.NumParser;
import com.softweb.api.store.utils.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final AuthorityService authorityService;

    public UserController(UserService userService, AuthenticationService authenticationService, AuthorityService authorityService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById (@PathVariable(name = "id") String userId) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        User requestedUser = userService.getUserById(userId);
        if (Objects.equals(authUser.getId(), requestedUser.getId()) || authUserAuthority == Authorities.ADMIN) {
            return new ResponseEntity<>(new UserAdminGetDto(requestedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UserDefaultGetDto(requestedUser), HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers (@RequestParam(name = "page") String page) {
        Integer pageValue = StringUtil.isBlankString(page) ? Integer.valueOf(0) : NumParser.parseIntOrNull(page);
        Authorities userAuthority = authenticationService.getAuthenticationAuthority();
        List<AbstractUserGetDto> result = new ArrayList<>();
        if (userAuthority == null) {
            List<User> users = userService.getUsers(pageValue);
            result.addAll(users.stream().map(UserDefaultGetDto::new).toList());
        }
        else {
            String authUsername = authenticationService.getAuthenticationName();
            List<User> users = userService.getUsers(pageValue);
            switch (userAuthority) {
                case USER -> {
                    result.addAll(
                            users.stream()
                                    .filter(item -> !item.getUsername().equals(authUsername))
                                    .map(UserDefaultGetDto::new)
                                    .toList()
                    );
                    result.addAll(
                            users.stream()
                                    .filter(item -> item.getUsername().equals(authUsername))
                                    .map(UserAdminGetDto::new)
                                    .toList()
                    );
                }
                case ADMIN -> result.addAll(users.stream().map(UserAdminGetDto::new).toList());
            }
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> postUser (@RequestBody UserPostDto userDto) {
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Authorities requestedUserAuthority = userDto.isAdmin() ? Authorities.ADMIN : Authorities.USER;
        if (requestedUserAuthority.equals(Authorities.ADMIN) &&
                (authUserAuthority == null || !authUserAuthority.equals(Authorities.ADMIN))) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        User savedUser = userService.saveUser(userDto);
        if (savedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        Authority authority = new Authority();
        authority.setUser(savedUser);
        authority.setAuthority(requestedUserAuthority.name());
        authority = authorityService.saveAuthority(authority);
        if (authority == null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            savedUser.setAuthority(authority);
            return new ResponseEntity<>(new UserAdminGetDto(savedUser), HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<?> putUser (@RequestBody UserPutDto userDto) {
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities requestedUserAuthority = userDto.isAdmin() ? Authorities.ADMIN : Authorities.USER;
        if (requestedUserAuthority.equals(Authorities.ADMIN) && !authUserAuthority.equals(Authorities.ADMIN)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        if (!Objects.equals(authUser.getId(), userDto.getId()) && !authUserAuthority.equals(Authorities.ADMIN)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        User savedUser = userService.saveUser(userDto);
        if (savedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        Authority authority = authorityService.getUserAuthority(savedUser);
        authority.setAuthority(requestedUserAuthority.name());
        authority = authorityService.saveAuthority(authority);
        if (Objects.isNull(authority)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            savedUser.setAuthority(authority);
            return new ResponseEntity<>(new UserAdminGetDto(savedUser), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser (@PathVariable(name = "id") String userId) {
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        if (Objects.requireNonNull(authUserAuthority) == Authorities.ADMIN) {
            userService.deleteUserById(userId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
