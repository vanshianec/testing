CREATE SCHEMA BankAccounts;

CREATE TABLE users
(
    id           BIGINT              NOT NULL AUTO_INCREMENT,
    username     VARCHAR(255) UNIQUE NOT NULL,
    email        VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(10),
    CONSTRAINT PK_USER PRIMARY KEY (id)
);

CREATE TABLE accounts
(
    id      BIGINT  NOT NULL AUTO_INCREMENT,
    balance DECIMAL NOT NULL CHECK (balance >= 0),
    user_id BIGINT,
    CONSTRAINT PK_ACCOUNT PRIMARY KEY (id),
    CONSTRAINT FK_USER_ACCOUNT FOREIGN KEY (user_id) REFERENCES USERS (id)
);

INSERT INTO users (username, email, phone_number)
VALUES ('pesho', 'pesho@abv.bg', '0889737144'),
       ('misho', 'misho@abv.bg', '0889324212'),
       ('maria', 'maria@abv.bg', '0874737313');

INSERT INTO accounts (balance, user_id)
VALUES (500, 1),
       (633.24, 2),
       (0.0, 3),
       (1200, 1),
       (1300, 2),
       (1000.01, 3);

SELECT u.username, a.id, a.balance
FROM users u
         INNER JOIN accounts a ON u.id = a.user_id
WHERE a.balance > 1000;

//Note: can be extracted in a procedure when using mysql dialect

INSERT INTO accounts (balance, user_id)
SELECT balance, (SELECT id FROM users WHERE username = 'newUser')
FROM accounts
WHERE accounts.user_id = (SELECT id FROM users WHERE username = 'oldUser');