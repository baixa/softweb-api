/*
    Create required sequences, that used as ids in tables (objects, that has 100 and less id is testing)
*/
CREATE SEQUENCE sq_application START 10001;
CREATE SEQUENCE sq_authority START 10001;
CREATE SEQUENCE sq_image START 10001;
CREATE SEQUENCE sq_installer START 10001;
CREATE SEQUENCE sq_operating_system START 10001;
CREATE SEQUENCE sq_user START 10001;
CREATE SEQUENCE sq_category START 10001;

CREATE TABLE license
(
    code VARCHAR(100)  NOT NULL,
    name TEXT NOT NULL,
    CONSTRAINT pk_license PRIMARY KEY (code)
);

CREATE TABLE users
(
    id           SERIAL      NOT NULL,
    username     VARCHAR(30) NOT NULL,
    full_name    VARCHAR(100),
    password     VARCHAR(64) NOT NULL,
    enabled      BOOLEAN     NOT NULL DEFAULT true,
    last_entered TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_username UNIQUE (username)
);

CREATE TABLE authority
(
    id          SERIAL          NOT NULL,
    user_id     BIGINT          NOT NULL,
    authority   VARCHAR(30)     NOT NULL,
    CONSTRAINT pk_authority PRIMARY KEY (id),
    CONSTRAINT uq_authority UNIQUE (user_id, authority)
);

ALTER TABLE authority
    ADD CONSTRAINT FK_AUTHORITY_ON_USERS FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE operating_system
(
    id   SERIAL       NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_operating_system PRIMARY KEY (id)
);

CREATE TABLE category
(
    id   SERIAL       NOT NULL,
    name VARCHAR(100) NOT NULL,
    image_path TEXT   NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE application
(
    id                SERIAL      NOT NULL,
    name              VARCHAR(50) NOT NULL,
    short_description VARCHAR(50),
    long_description  TEXT,
    logo_path         TEXT,
    license           VARCHAR(100),
    user_id           BIGINT      NOT NULL,
    category_id       BIGINT      NOT NULL,
    last_update       TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    downloads         INTEGER     NOT NULL,
    views             INTEGER     NOT NULL,
    CONSTRAINT pk_application PRIMARY KEY (id)
);

ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_USERS FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_LICENSE FOREIGN KEY (license) REFERENCES license (code);

CREATE TABLE image
(
    id             SERIAL NOT NULL,
    path           TEXT,
    application_id BIGINT NOT NULL,
    CONSTRAINT pk_image PRIMARY KEY (id)
);

ALTER TABLE image
    ADD CONSTRAINT FK_IMAGE_ON_APPLICATION FOREIGN KEY (application_id) REFERENCES application (id);

CREATE TABLE installer
(
    id             SERIAL  NOT NULL,
    application_id BIGINT,
    system_id      BIGINT,
    path           TEXT,
    version        VARCHAR(255),
    size           int8 NOT NULL,
    CONSTRAINT pk_installer PRIMARY KEY (id)
);

ALTER TABLE installer
    ADD CONSTRAINT FK_INSTALLER_ON_APPLICATION FOREIGN KEY (application_id) REFERENCES application (id);

ALTER TABLE installer
    ADD CONSTRAINT FK_INSTALLER_ON_SYSTEM FOREIGN KEY (system_id) REFERENCES operating_system (id);
