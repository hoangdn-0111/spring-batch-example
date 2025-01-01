--liquibase formatted sql
-- changeset your_name:1
CREATE TABLE EMPLOYEE_TBL
(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME        VARCHAR(255),
    DESIGNATION VARCHAR(255),
    SALARY      DOUBLE,
    DOJ         VARCHAR(255),
    BATCH_ID    BIGINT,
    STATUS      VARCHAR(255)
);

CREATE TABLE BATCH
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name     VARCHAR(255),
    file_size     VARCHAR(255),
    status        VARCHAR(255),
    created_by    VARCHAR(255),
    created_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(255),
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    additions     TEXT
);


