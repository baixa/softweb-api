package com.softweb.api.store.model.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserPostDto extends AbstractUserSaveDto{
    public UserPostDto(String username, String fullName, String password, boolean isAdmin, boolean isEnabled) {
        super(username, fullName, password, isAdmin, isEnabled);
    }
}
