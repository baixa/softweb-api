package com.softweb.api.store.model.dto.authority;

import com.softweb.api.store.model.entities.Authority;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.Authority} entity
 */
@Data
public class AuthorityDto implements Serializable {
    private final String name;

    public AuthorityDto(Authority authority) {
        this.name = authority.getAuthority();
    }
}