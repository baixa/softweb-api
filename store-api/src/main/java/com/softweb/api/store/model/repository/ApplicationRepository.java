package com.softweb.api.store.model.repository;

import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Application repository, that allow to perform
 * CRUD operations in corresponding table
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT a FROM Application a INNER JOIN User u ON u = a.user WHERE u.username = ?1 ORDER BY a.id")
    Page<Application> findApplicationsByUsername(String username, Pageable pageable);
}