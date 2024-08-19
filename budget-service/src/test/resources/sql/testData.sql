-- Insert into accounts
INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Main Checking Account', 'CHECKING', 1000, 'USD');

INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Savings Account', 'SAVINGS', 5000, 'USD');

INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Main Checking Account', 'CHECKING', 1000, 'USD');

INSERT INTO accounts (user_id, name, type, balance, currency)
VALUES (1, 'Savings Account', 'SAVINGS', 5000, 'USD');

-- Assumes account_id 1 exists in the accounts table
INSERT INTO transactions (user_id, type, transaction_value, description, account_id, timestamp)
VALUES (1, 'INCOME', 500, 'Initial deposit', 3, CURRENT_TIMESTAMP);

-- Assumes account_id 2 exists in the accounts table
INSERT INTO transactions (user_id, type, transaction_value, description, account_id, timestamp)
VALUES (1, 'EXPENSE', -200, 'ATM withdrawal', 4, CURRENT_TIMESTAMP);

INSERT INTO budgets (user_id, name, maximum_allowed, budget_interval, transaction_id, account_id)
VALUES (1, 'Monthly Budget', 1500, 'DAILY', 2, 3);

INSERT INTO budgets (user_id, name, maximum_allowed, budget_interval, transaction_id, account_id)
VALUES (1, 'Savings Goal', 2000, 'WEEKLY', 2, 4);