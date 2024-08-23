INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Main Checking Account', 'CHECKING', 1000, 'USD');

INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Savings Account', 'SAVINGS', 5000, 'USD');

INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Main Checking Account', 'CHECKING', 1000, 'USD');

INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Savings Account', 'SAVINGS', 5000, 'USD');

INSERT INTO categories (user_id, name)
VALUES (1, 'General');

INSERT INTO categories (user_id, name)
VALUES (1, 'General');

INSERT INTO categories (user_id, name)
VALUES (1, 'General');

INSERT INTO transactions (user_id, type, transaction_value, description, category_id, account_id, timestamp)
VALUES (1, 'INCOME', 500, 'Initial deposit', 3, 1, CURRENT_TIMESTAMP);

INSERT INTO transactions (user_id, type, transaction_value, description, category_id, account_id, timestamp)
VALUES (1, 'EXPENSE', -200, 'ATM withdrawal', 3, 1, CURRENT_TIMESTAMP);

INSERT INTO budgets (user_id, name, maximum_allowed, budget_interval, category_id, account_id)
VALUES (1, 'Monthly Budget', 1500, 'DAILY', 1, 1);

INSERT INTO budgets (user_id, name, maximum_allowed, budget_interval, category_id, account_id)
VALUES (1, 'Savings Goal', 2000, 'WEEKLY', 1, 1);