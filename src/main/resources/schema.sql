DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS companies;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE companies
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  address          VARCHAR                 NOT NULL,
  tin              BIGINT                  NOT NULL,
  phone            VARCHAR                 NOT NULL
);

CREATE TABLE employees
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  company_id       INTEGER                 NOT NULL,
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  birthday         TIMESTAMP               NOT NULL
);
