DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS passports;
DROP TABLE IF EXISTS addresses;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE passports
(
    user_id        INT PRIMARY KEY REFERENCES users (id),
    passportNumber VARCHAR(255) NOT NULL
);

CREATE TABLE addresses
(
    id      SERIAL PRIMARY KEY,
    user_id INT REFERENCES users (id),
    address VARCHAR(255) NOT NULL
);