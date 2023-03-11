package com.softweb.api.store.model.dto.user;

import com.softweb.api.store.model.entities.Authority;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AbstractUserSaveDto implements Serializable {
    private String username;
    private String fullName;
    private String password;
    private boolean isAdmin;
    private Authority authority;
    private LocalDateTime lastEntered = LocalDateTime.now();

    public AbstractUserSaveDto(String username, String fullName, String password, boolean isAdmin) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public AbstractUserSaveDto() {
    }
}
