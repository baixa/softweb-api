package com.softweb.api.store.model.repository;

import com.softweb.api.store.model.entities.Authority;
import com.softweb.api.store.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, User> {
    @Query("SELECT a FROM Authority as a WHERE a.user = ?1")
    Authority findByUser(User user);
}
