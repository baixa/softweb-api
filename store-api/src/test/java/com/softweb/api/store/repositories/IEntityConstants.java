package com.softweb.api.store.repositories;

import com.softweb.api.store.model.entities.License;

import java.util.List;

/**
 * Constants, that are used in tests
 */
public interface IEntityConstants {
    String LICENSE_TEST_CODE_DEFAULT = "TEST";
    String LICENSE_TEST_NAME_DEFAULT = "Test license";
    String LICENSE_TEST_CODE_UPDATED = "LCS";
    String LICENSE_TEST_NAME_UPDATED = "New license name";
    String LICENSE_TEST_CODE_ABSENT = "ABSENT";
    String LICENSE_TEST_CODE_1 = "TST1";
    String LICENSE_TEST_NAME_1 = "Test license #1";
    String LICENSE_TEST_CODE_2 = "TST2";
    String LICENSE_TEST_NAME_2 = "Test license #2";
    String LICENSE_TEST_CODE_3 = "TST3";
    String LICENSE_TEST_NAME_3 = "Test license #3";


    List<License> MOCK_LICENSE_LIST = List.of(
            new License(LICENSE_TEST_CODE_1, LICENSE_TEST_NAME_1),
            new License(LICENSE_TEST_CODE_2, LICENSE_TEST_NAME_2),
            new License(LICENSE_TEST_CODE_3, LICENSE_TEST_NAME_3)
    );

    License SAVED_LICENSE = new License(LICENSE_TEST_CODE_UPDATED, LICENSE_TEST_NAME_UPDATED);
}
