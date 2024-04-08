INSERT INTO "user" (email, password)
VALUES ('First', '1111'),
       ('Second', '2222'),
       ('Third', '3333');

INSERT INTO passport (user_id, passportnumber)
VALUES ('1', '1234'),
       ('2', '4321'),
       ('3', '4567');

INSERT INTO address (user_id, address)
VALUES ('1', 'address1'),
       ('1', 'address2'),
       ('1', 'address3'),
       ('2', 'address4'),
       ('2', 'address5'),
       ('2', 'address6'),
       ('3', 'address7'),
       ('3', 'address8'),
       ('3', 'address9');
