DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS passport;
DROP TABLE IF EXISTS address;

CREATE TABLE "user"
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE passport
(
    user_id        INT PRIMARY KEY REFERENCES "user" (id),
    passportNumber VARCHAR(255) NOT NULL
);

CREATE TABLE address
(
    id      SERIAL PRIMARY KEY,
    user_id INT REFERENCES "user" (id),
    address VARCHAR(255) NOT NULL
);