DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS passport;
DROP TABLE IF EXISTS address;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE passport
(
    user_id        INT PRIMARY KEY REFERENCES users (id),
    passportNumber VARCHAR(255) NOT NULL
);

CREATE TABLE address
(
    id      SERIAL PRIMARY KEY,
    user_id INT REFERENCES users (id),
    address VARCHAR(255) NOT NULL
);