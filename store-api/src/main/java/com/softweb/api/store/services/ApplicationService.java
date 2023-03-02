package com.softweb.api.store.services;

import com.softweb.api.store.model.dto.application.ApplicationPostDto;
import com.softweb.api.store.model.dto.application.ApplicationPutDto;
import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.model.repository.ApplicationRepository;
import com.softweb.api.store.model.repository.RepositoryConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Application getApplicationById (Long applicationId) {
        return applicationRepository.findById(applicationId).orElse(null);
    }

    public List<Application> getApplications (Integer page) {
        Page<Application> applicationPage = applicationRepository.findAll(
                PageRequest.of(page, RepositoryConstants.COUNT_PAGE_ELEMENTS, Sort.by(Sort.Direction.ASC, "id"))
        );
        return applicationPage.getContent();
    }

    public List<Application> getApplicationsByCategory (Integer page, Category category) {
        Page<Application> applicationPage = applicationRepository.findAllByCategory(
                category,
                PageRequest.of(page, RepositoryConstants.COUNT_PAGE_ELEMENTS, Sort.by(Sort.Direction.ASC, "id"))
        );
        return applicationPage.getContent();
    }

    public void saveApplication(ApplicationPostDto applicationDto) {
        Application application = new Application(applicationDto);
        applicationRepository.save(application);
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
            application.setLogoPath(applicationPutDto.getLogoBase64());
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
}
