package com.softweb.api.store.model.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class UserPostDto extends AbstractUserSaveDto implements Serializable {
    public UserPostDto(String username, String fullName, String password, boolean isAdmin) {
        super(username, fullName, password, isAdmin);
    }

    public UserPostDto(){}
}
