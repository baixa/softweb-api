package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.user.UserGetDto;
import com.softweb.api.store.model.dto.user.UserPostDto;
import com.softweb.api.store.model.dto.user.UserPutDto;
import com.softweb.api.store.model.entities.Authorities;
import com.softweb.api.store.model.entities.Authority;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.services.AuthenticationService;
import com.softweb.api.store.services.AuthorityService;
import com.softweb.api.store.services.UserService;
import com.softweb.api.store.utils.NumParser;
import com.softweb.api.store.utils.ResponseError;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for published applications. Provides getting, creating, modifying, and deleting users
 * @see User
 */
@RestController
@RequestMapping(value = "/v1/user")
@Tag(name = "User", description = "API provides ability to manipulate users")
public class UserController {
    /**
     * Service, that provides ability to interaction with the User entity
     */
    private final UserService userService;

    /**
     * Service providing information about an authorized user
     */
    private final AuthenticationService authenticationService;

    /**
     * Service, that provides ability to interaction with the Authority entity
     */
    private final AuthorityService authorityService;

    /**
     * Controller
     *
     * @param userService Service of user
     * @param authenticationService Service of authentication
     * @param authorityService Service of authority
     */
    public UserController(UserService userService,
                          AuthenticationService authenticationService,
                          AuthorityService authorityService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.authorityService = authorityService;
    }

    /**
     * Returns an object of the User class whose id matches the one passed
     *
     * @param userId - id of requested user
     * @return requested user
     */
    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get user by it's id",
            description = "Returns an object of the User class whose id matches the one passed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "User with given id is absent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getUserById (@PathVariable(name = "id") String userId) {
        if (NumParser.parseIntOrNull(userId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid ID"), HttpStatus.BAD_REQUEST);
        User requestedUser = userService.getUserById(userId);
        if (Objects.isNull(requestedUser))
            return ResponseEntity.of(Optional.empty());
        return ResponseEntity.ok(new UserGetDto(requestedUser));
    }

    /**
     * Endpoint to allow clients check user auth credentials
     *
     * @return Status of authentication
     */
    @PostMapping("/auth")
    @Hidden
    public ResponseEntity<UserGetDto> auth() {
        return ResponseEntity.ok(new UserGetDto(authenticationService.getAuthenticatedUser()));
    }

    /**
     * Returns a list of users, that the size corresponds to the transferred
     *
     * @param pageable Data of elements quality
     * @return List of users
     */
    @GetMapping
    @Operation(
            summary = "Get users",
            description = "Returns a list of users, that the size corresponds to the transferred"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getUsers (@ParameterObject Pageable pageable) {
        List<User> users = userService.getUsers(pageable);
        List<UserGetDto> result = users.stream().map(UserGetDto::new).toList();
        return ResponseEntity.ok(result);
    }

    /**
     * Create user that are based on request body data
     *
     * @param userDto Data of creatable users
     * @return Created application
     */
    @PostMapping
    @Operation(
            summary = "Create users",
            description = "Create user that are based on request body data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns created user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content) })
    public ResponseEntity<?> postUser (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Creatable user", required = true,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserPostDto.class))})
            @RequestBody UserPostDto userDto) {
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Authorities requestedUserAuthority = userDto.isAdmin() ? Authorities.ADMIN : Authorities.USER;
        if (requestedUserAuthority.equals(Authorities.ADMIN) &&
                (authUserAuthority == null || !authUserAuthority.equals(Authorities.ADMIN))) {
            return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to edit this user"),
                    HttpStatus.FORBIDDEN);
        }
        User savedUser = userService.saveUser(userDto);
        if (savedUser == null) {
            return new ResponseEntity<>(new ResponseError("Access denied!"),
                    HttpStatus.FORBIDDEN);
        }
        Authority authority = new Authority();
        authority.setUser(savedUser);
        authority.setAuthority(requestedUserAuthority.name());
        authority = authorityService.saveAuthority(authority);
        if (authority == null) {
            return new ResponseEntity<>(new ResponseError("Access denied!"),
                    HttpStatus.FORBIDDEN);
        } else {
            savedUser.setAuthority(authority);
            return new ResponseEntity<>(new UserGetDto(savedUser), HttpStatus.CREATED);
        }
    }

    /**
     * Edit user that are based on request body data
     *
     * @param userDto Data of editable user
     * @return Edited user
     */
    @PutMapping
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Edit user",
            description = "Edit user that are based on request body data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns edited user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)})
    public ResponseEntity<?> putUser (@RequestBody UserPutDto userDto) {
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities requestedUserAuthority = userDto.isAdmin() ? Authorities.ADMIN : Authorities.USER;
        if (requestedUserAuthority.equals(Authorities.ADMIN) && !authUserAuthority.equals(Authorities.ADMIN)) {
            return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to edit this user"),
                    HttpStatus.FORBIDDEN);
        }
        if (!Objects.equals(authUser.getId(), userDto.getId()) && !authUserAuthority.equals(Authorities.ADMIN)) {
            return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to edit this user"),
                    HttpStatus.FORBIDDEN);
        }
        User savedUser = userService.saveUser(userDto);
        if (savedUser == null) {
            return new ResponseEntity<>(new ResponseError("Access denied"),
                    HttpStatus.FORBIDDEN);
        }
        Authority authority = authorityService.getUserAuthority(savedUser);
        authority.setAuthority(requestedUserAuthority.name());
        authority = authorityService.saveAuthority(authority);
        if (Objects.isNull(authority)) {
            return new ResponseEntity<>(new ResponseError("Access denied"),
                    HttpStatus.FORBIDDEN);
        } else {
            savedUser.setAuthority(authority);
            return new ResponseEntity<>(new UserGetDto(savedUser), HttpStatus.OK);
        }
    }

    /**
     * Delete user that are based on request body data
     *
     * @param userId ID of removable user
     * @return Status of removing
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Delete user",
            description = "Delete user that are based on request body data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns status of user removing"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)})
    public ResponseEntity<?> deleteUser (@PathVariable(name = "id") String userId) {
        if (NumParser.parseIntOrNull(userId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid user ID"), HttpStatus.BAD_REQUEST);
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        if (Objects.requireNonNull(authUserAuthority) == Authorities.ADMIN) {
            userService.deleteUserById(userId);
            return new ResponseEntity<>(new ResponseError("User removed successfully!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to remove this user"),
                HttpStatus.FORBIDDEN);
    }
}
