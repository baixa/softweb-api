package com.softweb.api.store.event;

import com.softweb.api.store.services.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {

    private final UserService userService;

    public AuthenticationEvents(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        userService.authUser(success.getAuthentication().getName());
    }
}
