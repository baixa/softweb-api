package com.softweb.api.store.model.dto.user;

import com.softweb.api.store.model.entities.Authority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AbstractUserSaveDto {
    private final String username;
    private final String fullName;
    private final String password;
    private final boolean isAdmin;
    private final boolean isEnabled;
    private Authority authority;
    private LocalDateTime lastEntered;

    public AbstractUserSaveDto(String username, String fullName, String password, boolean isAdmin, boolean isEnabled) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isEnabled = isEnabled;
        this.lastEntered = LocalDateTime.now();
    }
}
