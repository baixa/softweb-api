package com.softweb.api.store.services;

import com.softweb.api.store.model.dto.application.ApplicationPostDto;
import com.softweb.api.store.model.dto.application.ApplicationPutDto;
import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.model.repository.ApplicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application getApplicationById (String applicationId) {
        return applicationRepository.findById(Long.valueOf(applicationId)).orElse(null);
    }

    public List<Application> getApplications (Pageable pageable) {
        Page<Application> applicationPage = applicationRepository.findAll(pageable);
        return applicationPage.getContent();
    }

    public List<Application> getApplicationsByCategory (Pageable pageable, Category category) {
        Page<Application> applicationPage = applicationRepository.findAllByCategory(category,pageable);
        return applicationPage.getContent();
    }

    public Application saveApplication(ApplicationPostDto applicationDto) {
        Application application = new Application(applicationDto);
        return applicationRepository.save(application);
    }

    public void deleteApplicationById (String applicationId) {
        applicationRepository.deleteById(Long.valueOf(applicationId));
    }

    public Application saveApplication(ApplicationPutDto applicationPutDto) {
        Application application = getApplicationById(applicationPutDto.getId());
        if (Objects.isNull(application)) {
            return null;
        }
        else {
            application.setName(applicationPutDto.getName());
            application.setShortDescription(applicationPutDto.getShortDescription());
            application.setLongDescription(applicationPutDto.getLongDescription());
            application.setLicense(applicationPutDto.getLicense());
            application.setLogoPath(applicationPutDto.getLogo());
            application.setLastUpdate(applicationPutDto.getLastUpdate());
            return applicationRepository.save(application);
        }
    }

    public boolean isUserOwner(ApplicationPutDto applicationPutDto, User user) {
        Application application = getApplicationById(applicationPutDto.getId());
        if (Objects.isNull(application)) {
            return false;
        }
        else {
            return Objects.equals(application.getUser().getId(), user.getId());
        }
    }

    public boolean isUserOwner(String applicationId, User user) {
        Application application = getApplicationById(applicationId);
        if (Objects.isNull(application)) {
            return false;
        }
        else {
            return Objects.equals(application.getUser().getId(), user.getId());
        }
    }

    public List<Application> getApplicationsByUser(Pageable pageable, User user) {
        Page<Application> applicationPage = applicationRepository.findByUser(user, pageable);
        return applicationPage.getContent();
    }

    public Long getApplicationsCount() {
        return applicationRepository.count();
    }

    public Long getApplicationsCountByCategory(Category category) {
        return applicationRepository.countByCategory(category);
    }

    public Long getApplicationsCountByUser(User user) {
        return applicationRepository.countByUser(user);
    }
}
