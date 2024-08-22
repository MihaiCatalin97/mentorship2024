DROP TABLE IF EXISTS budgets;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
                          id INT GENERATED ALWAYS AS IDENTITY,
                          user_id INT NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          type VARCHAR(50) NOT NULL,
                          balance INT NOT NULL,
                          currency VARCHAR(255) NOT NULL,
                          PRIMARY KEY(id)
);

CREATE TABLE categories (
                            id INT GENERATED ALWAYS AS IDENTITY,
                            user_id INT NOT NULL,
                            name VARCHAR(100) NOT NULL,
                            PRIMARY KEY(id)
);

CREATE TABLE transactions (
                              id INT GENERATED ALWAYS AS IDENTITY,
                              user_id INT NOT NULL,
                              type VARCHAR(50) NOT NULL,
                              transaction_value INT NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              category_id INT NOT NULL,
                              account_id INT NOT NULL,
                              timestamp TIMESTAMP NOT NULL,
                              PRIMARY KEY(id),
                              CONSTRAINT fk_category
                                  FOREIGN KEY (category_id)
                                      REFERENCES categories(id),
                              CONSTRAINT fk_account
                                  FOREIGN KEY (account_id)
                                      REFERENCES accounts(id)
);

CREATE TABLE budgets (
                         id INT GENERATED ALWAYS AS IDENTITY,
                         user_id INT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         maximum_allowed INT NOT NULL,
                         budget_interval VARCHAR(50) NOT NULL,
                         category_id INT NOT NULL,
                         account_id INT NOT NULL,
                         PRIMARY KEY(id),
                         CONSTRAINT fk_budget_category
                             FOREIGN KEY (category_id)
                                 REFERENCES categories(id),
                         CONSTRAINT fk_budget_account
                             FOREIGN KEY (account_id)
                                 REFERENCES accounts(id)
);
