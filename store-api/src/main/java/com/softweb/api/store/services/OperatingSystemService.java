package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.OperatingSystem;
import com.softweb.api.store.model.repository.OperatingSystemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatingSystemService {
    private final OperatingSystemRepository operatingSystemRepository;

    public OperatingSystemService(OperatingSystemRepository operatingSystemRepository) {
        this.operatingSystemRepository = operatingSystemRepository;
    }

    public OperatingSystem getOperatingSystemById (String operatingSystemId) {
        return operatingSystemRepository.findById(Long.valueOf(operatingSystemId)).orElse(null);
    }

    public List<OperatingSystem> getOperatingSystems () {
        return operatingSystemRepository.findAll();
    }
}
