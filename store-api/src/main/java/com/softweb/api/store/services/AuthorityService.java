package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Authority;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.model.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority getUserAuthority (User user) {
        return authorityRepository.findByUser(user);
    }

    public Authority saveAuthority (Authority authority) {
        return authorityRepository.save(authority);
    }
}
