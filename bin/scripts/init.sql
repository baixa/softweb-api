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
    enabled      BOOLEAN     NOT NULL,
    last_entered TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user PRIMARY KEY (id)

);

CREATE TABLE authority
(
    user_id BIGINT      NOT NULL,
    authority VARCHAR(30) NOT NULL,
    CONSTRAINT pk_authority PRIMARY KEY (user_id)
);

ALTER TABLE authority
    ADD CONSTRAINT FK_AUTHORITY_ON_USERS FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE operating_system
(
    id   SERIAL       NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_operating_system PRIMARY KEY (id)
);

CREATE TABLE application
(
    id                SERIAL      NOT NULL,
    name              VARCHAR(20) NOT NULL,
    short_description VARCHAR(50) NOT NULL,
    long_description  VARCHAR(255),
    logo_path         VARCHAR(255),
    license           VARCHAR(100),
    user_id      BIGINT,
    last_update       TIMESTAMP WITHOUT TIME ZONE,
    downloads         INTEGER     NOT NULL,
    views             INTEGER     NOT NULL,
    CONSTRAINT pk_application PRIMARY KEY (id)
);

ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_USERS FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_LICENSE FOREIGN KEY (license) REFERENCES license (code);

CREATE TABLE image
(
    id             SERIAL NOT NULL,
    path           VARCHAR(255),
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
    path           VARCHAR(255),
    version        VARCHAR(255),
    size           INTEGER NOT NULL,
    CONSTRAINT pk_installer PRIMARY KEY (id)
);

ALTER TABLE installer
    ADD CONSTRAINT FK_INSTALLER_ON_APPLICATION FOREIGN KEY (application_id) REFERENCES application (id);

ALTER TABLE installer
    ADD CONSTRAINT FK_INSTALLER_ON_SYSTEM FOREIGN KEY (system_id) REFERENCES operating_system (id);
