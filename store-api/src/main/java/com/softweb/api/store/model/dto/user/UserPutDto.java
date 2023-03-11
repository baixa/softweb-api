package com.softweb.api.store.model.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserPutDto extends AbstractUserSaveDto {
    private Long id;

    public UserPutDto(Long id, String username, String fullName, String password, boolean isAdmin) {
        super(username, fullName, password, isAdmin);
        this.id = id;
    }

    public UserPutDto() {}
}
