package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application getApplicationById (String applicationId) {
        return applicationRepository.findById(Long.valueOf(applicationId)).orElse(null);
    }

    public List<Application> getApplications () {
        return applicationRepository.findAll();
    }

    public void saveApplication (Application application) {
        applicationRepository.save(application);
    }

    public void deleteApplication (Application application) {
        applicationRepository.delete(application);
    }

    public void deleteApplicationById (String applicationId) {
        applicationRepository.deleteById(Long.valueOf(applicationId));
    }
}
