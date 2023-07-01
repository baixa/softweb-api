package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.repository.ApplicationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationService applicationService;

    private final static String TEST_APP_NAME_1 = "Test app 1";
    private final static String TEST_APP_NAME_2 = "Test app 2";
    private final static String TEST_APP_NAME_3 = "Test app 3";

    private final static Long TEST_APP_ID_1 = 1L;
    private final static Long TEST_APP_ID_2 = 2L;
    private final static Long TEST_APP_ID_3 = 3L;


    @BeforeEach
    void setUp() {
        Application testElem1 = new Application();
        testElem1.setName(TEST_APP_NAME_1);
        testElem1.setId(TEST_APP_ID_1);

        Application testElem2 = new Application();
        testElem2.setName(TEST_APP_NAME_2);
        testElem2.setId(TEST_APP_ID_2);

        Application testElem3 = new Application();
        testElem3.setName(TEST_APP_NAME_3);
        testElem3.setId(TEST_APP_ID_3);

        lenient().when(applicationRepository.findAll(PageRequest.of(0, 2)))
                .thenReturn(new PageImpl<>(List.of(testElem1, testElem2, testElem3)));
        lenient().when(applicationRepository.findById(TEST_APP_ID_1)).thenReturn(Optional.of(testElem1));
        lenient().when(applicationRepository.findById(TEST_APP_ID_2)).thenReturn(Optional.of(testElem2));
        lenient().when(applicationRepository.findById(TEST_APP_ID_3)).thenReturn(Optional.of(testElem3));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getApplicationById() {
        Application expectedApp = new Application();
        expectedApp.setId(TEST_APP_ID_1);
        expectedApp.setName(TEST_APP_NAME_1);

        Application existsValue = applicationService.getApplicationById(String.valueOf(TEST_APP_ID_1));
        Assertions.assertEquals(existsValue, expectedApp);
    }

    @Test
    void getApplications() {
        Application expectedApp1 = new Application();
        expectedApp1.setId(TEST_APP_ID_1);
        expectedApp1.setName(TEST_APP_NAME_1);

        Application expectedApp2 = new Application();
        expectedApp2.setId(TEST_APP_ID_2);
        expectedApp2.setName(TEST_APP_NAME_2);

        List<Application> expectedResult = List.of(expectedApp1, expectedApp2);
        List<Application> existsResult = applicationService.getApplications(PageRequest.of(0, 2));
        Assertions.assertEquals(expectedResult, existsResult);
    }
}