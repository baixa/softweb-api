package com.softweb.api.store.model.dto.user;

import com.softweb.api.store.model.entities.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.User} entity
 */
@Getter
public class UserDefaultGetDto extends AbstractUserGetDto implements Serializable {
    public UserDefaultGetDto(User user) {
        super(user);
    }
}