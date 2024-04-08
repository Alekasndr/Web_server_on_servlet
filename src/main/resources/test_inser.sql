INSERT INTO "user" (email, password)
VALUES ('First', '1111'),
       ('Second', '2222'),
       ('Third', '3333'); -- и тд, так меньше места будет скрипт щанимать

INSERT INTO "user" (email, password)
VALUES ('Second', '2222');

INSERT INTO "user" (email, password)
VALUES ('Third', '3333');

INSERT INTO passport (user_id, passportnumber)
VALUES ('1', '1234');

INSERT INTO passport (user_id, passportnumber)
VALUES ('2', '4321');

INSERT INTO passport (user_id, passportnumber)
VALUES ('3', '4567');

INSERT INTO address (user_id, address)
VALUES ('1', 'address1');

INSERT INTO address (user_id, address)
VALUES ('1', 'address2');

INSERT INTO address (user_id, address)
VALUES ('1', 'address3');

INSERT INTO address (user_id, address)
VALUES ('2', 'address4');

INSERT INTO address (user_id, address)
VALUES ('2', 'address5');

INSERT INTO address (user_id, address)
VALUES ('2', 'address6');

INSERT INTO address (user_id, address)
VALUES ('3', 'address7');

INSERT INTO address (user_id, address)
VALUES ('3', 'address8');

INSERT INTO address (user_id, address)
VALUES ('3', 'address9');
;