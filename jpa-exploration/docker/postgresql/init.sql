CREATE USER jpa_exploration
    WITH PASSWORD 'jpa_exploration';

GRANT ALL PRIVILEGES
    ON DATABASE postgres
    TO jpa_exploration;

ALTER ROLE jpa_exploration
    SET search_path = jpa_exploration;

CREATE SCHEMA
    IF NOT EXISTS jpa_exploration
    AUTHORIZATION jpa_exploration;

ALTER SCHEMA jpa_exploration
    OWNER TO jpa_exploration;

GRANT USAGE
    ON SCHEMA jpa_exploration
    TO jpa_exploration;

CREATE TABLE jpa_exploration.TEST
(
    ID_1      VARCHAR(4),
    ID_2      VARCHAR(4),
    CHARACTER VARCHAR(3),
    NUMERIC   NUMERIC(7),
    DATETIME  DATE,
    CONSTRAINT PK_TEST
        PRIMARY KEY (ID_1, ID_2)
);

CREATE INDEX CI_TEST
    ON jpa_exploration.TEST (ID_1, ID_2);


