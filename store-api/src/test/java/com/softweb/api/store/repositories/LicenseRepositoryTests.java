package com.softweb.api.store.repositories;

import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.model.repository.LicenseRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests check LicenseRepository class
 * <p>
 *
 * <h3>Примечание!!!</h3>
 * <p>В данном классе используются <b>mock-объект</b> (эмуляция экземпляра класса), т.к. данный класс напрямую связан с источником данных.
 * Однако проверить работоспособность на настоящей БД нет возможности в виду того, что ее нет. Экземпляр источника данных
 * создается при запуске сети контейнеров Docker как один из участников сети и до запуска сборки подключение к нему невозможно!
 * Поэтому используется mock-объект для оценки работы <b>САМОГО</b> метода, а не взаимодействия с базой данных</p>
 */
public class LicenseRepositoryTests {

    /**
     * Object of testing class
     */
    @Mock
    private LicenseRepository licenseRepository;

    /**
     * Set up mock object behavior
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        License license = new License(IEntityConstants.LICENSE_TEST_CODE_DEFAULT, IEntityConstants.LICENSE_TEST_NAME_DEFAULT);
        List<License> licenses = IEntityConstants.MOCK_LICENSE_LIST;
        License savedLicense = IEntityConstants.SAVED_LICENSE;

        when(licenseRepository.findById(IEntityConstants.LICENSE_TEST_CODE_DEFAULT)).thenReturn(Optional.of(license));
        when(licenseRepository.findById(IEntityConstants.LICENSE_TEST_CODE_UPDATED)).thenReturn(Optional.of(savedLicense));
        when(licenseRepository.findAll()).thenReturn(licenses);
        when(licenseRepository.count()).thenReturn((long) licenses.size());
        when(licenseRepository.save(savedLicense)).thenReturn(savedLicense);
    }


    /**
     * Test method <b>findById(String id)</b>. Testing method ensures that an object is retrieved from the data source by its identifier
     */
    @Test
    public void findByCode() {
        License foundLicense = licenseRepository.findById(IEntityConstants.LICENSE_TEST_CODE_DEFAULT).orElse(null);
        License standardLicense = new License(IEntityConstants.LICENSE_TEST_CODE_DEFAULT, IEntityConstants.LICENSE_TEST_NAME_DEFAULT);
        Assertions.assertEquals(foundLicense, standardLicense);
    }

    /**
     * Test method <b>findById(String id)</b>. Testing method ensures that an object is retrieved from the data source by its identifier.
     * This test check, that method returns null if id is absent in DB
     */
    @Test
    public void findByAbsentCode() {
        License foundLicense = licenseRepository.findById(IEntityConstants.LICENSE_TEST_CODE_ABSENT).orElse(null);
        Assertions.assertNull(foundLicense);
    }

    /**
     * Test method <b>findAll()</b>. Testing method ensures that all objects are retrieved from the data source
     */
    @Test
    public void findAll() {
        List<License> foundLicenses = licenseRepository.findAll();
        Assertions.assertEquals(foundLicenses.size(), IEntityConstants.MOCK_LICENSE_LIST.size());
        Assertions.assertEquals(foundLicenses, IEntityConstants.MOCK_LICENSE_LIST);
    }

    /**
     * Test method <b>count()</b>. Testing method returns the number of objects in the database in
     * the table corresponding to the repository class.
     */
    @Test
    public void count() {
        long countLicenses = licenseRepository.count();
        Assertions.assertEquals(countLicenses, IEntityConstants.MOCK_LICENSE_LIST.size());
    }

    /**
     * Test method <b>save(License license)</b>. Testing method save object in the database in
     * the table corresponding to the repository class and returns it.
     */
    @Test
    public void save() {
        License savedLicense = IEntityConstants.SAVED_LICENSE;
        Assertions.assertEquals(licenseRepository.save(savedLicense), savedLicense);
        Assertions.assertEquals(licenseRepository.findById(
                IEntityConstants.LICENSE_TEST_CODE_UPDATED).orElse(null),
                IEntityConstants.SAVED_LICENSE
        );
    }
}
