DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS budgets;

CREATE TABLE accounts (
                          id INT GENERATED ALWAYS AS IDENTITY,
                          user_id INT NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          type VARCHAR(50) NOT NULL,
                          balance INT NOT NULL,
                          currency VARCHAR(255) NOT NULL,
                          PRIMARY KEY(id)
);

CREATE TABLE transactions (
                              id INT GENERATED ALWAYS AS IDENTITY,
                              user_id INT NOT NULL,
                              type VARCHAR(50) NOT NULL,
                              value INT NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              account_id INT NOT NULL,
                              timestamp TIMESTAMP NOT NULL,
                              PRIMARY KEY(id),
                              CONSTRAINT fk_account
                                  FOREIGN KEY (account_id)
                                      REFERENCES accounts(id)
);

CREATE TABLE budgets (
                         id INT GENERATED ALWAYS AS IDENTITY,
                         user_id INT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         maximum_allowed INT NOT NULL,
                         interval VARCHAR(50) NOT NULL,
                         current_usage INT NOT NULL,
                         transaction_id INT NOT NULL,
                         account_id INT NOT NULL,
                         PRIMARY KEY(id),
                         CONSTRAINT fk_transaction
                             FOREIGN KEY (transaction_id)
                                 REFERENCES transactions(id),
                         CONSTRAINT fk_account
                             FOREIGN KEY (account_id)
                                 REFERENCES accounts(id)
);
