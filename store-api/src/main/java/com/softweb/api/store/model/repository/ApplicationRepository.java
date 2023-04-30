package com.softweb.api.store.model.repository;

import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Application repository, that allow to perform
 * CRUD operations in corresponding table
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    long countByCategory(Category category);
    long countByUser(User user);

    Page<Application> findByUser(User user, Pageable pageable);

    Page<Application> findAllByCategory(Category category, Pageable pageable);

    List<Application> findAllByOrderByNameAsc();
}