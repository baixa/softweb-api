package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Installer;
import com.softweb.api.store.model.repository.InstallerRepository;
import org.springframework.stereotype.Service;

@Service
public class InstallerService {
    private final InstallerRepository installerRepository;

    public InstallerService(InstallerRepository installerRepository) {
        this.installerRepository = installerRepository;
    }

    public Installer getInstallerById (String installerId) {
        return installerRepository.findById(Long.valueOf(installerId)).orElse(null);
    }

    public void saveInstaller (Installer installer) {
        installerRepository.save(installer);
    }

    public void deleteInstallerById (String installerId) {
        installerRepository.deleteById(Long.valueOf(installerId));
    }

    public long getCountInstallersByPath(String installerPath) {
        return installerRepository.countByInstallerPath(installerPath);
    }
}
