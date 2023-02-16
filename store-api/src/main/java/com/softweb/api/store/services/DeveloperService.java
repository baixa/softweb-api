package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Developer;
import com.softweb.api.store.model.repository.DeveloperRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public Developer getDeveloperById (String developerId) {
        return developerRepository.findById(Long.valueOf(developerId)).orElse(null);
    }

    public List<Developer> getDevelopers () {
        return developerRepository.findAll();
    }

    public void saveDeveloper (Developer developer) {
        developerRepository.save(developer);
    }

    public void deleteDeveloper (Developer developer) {
        developerRepository.delete(developer);
    }

    public void deleteDeveloperById (String developerId) {
        developerRepository.deleteById(Long.valueOf(developerId));
    }
}
