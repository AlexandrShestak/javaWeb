CREATE TABLE scand.user
(
    login VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL
);
ALTER TABLE scand.user ADD CONSTRAINT unique_login UNIQUE (login);