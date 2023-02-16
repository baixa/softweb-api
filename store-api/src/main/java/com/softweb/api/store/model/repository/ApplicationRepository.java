package com.softweb.api.store.model.repository;

import com.softweb.api.store.model.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Application repository, that allow to perform
 * CRUD operations in corresponding table
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}