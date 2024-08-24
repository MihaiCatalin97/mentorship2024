DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                            id INTEGER GENERATED ALWAYS AS IDENTITY,
                            email VARCHAR(128) UNIQUE NOT NULL,
                            first_name VARCHAR(128) NOT NULL,
                            last_name VARCHAR(128) NOT NULL,
                            hashed_password VARCHAR(128) NOT NULL,
                            verification_token VARCHAR(128),
                            created_at TIMESTAMP NOT NULL,
                            last_sent_verification_notification TIMESTAMP,
                            last_changed_password TIMESTAMP,
                            password_change_token TIMESTAMP,
                            last_sent_password_change_token TIMESTAMP,
                            verified_at TIMESTAMP,
                            PRIMARY KEY (id)
);

CREATE TABLE sessions (
                            id INTEGER GENERATED ALWAYS AS IDENTITY,
                            session_key VARCHAR(128) NOT NULL UNIQUE,
                            expires_at TIMESTAMP NOT NULL,
                            user_id INT NOT NULL,
                            PRIMARY KEY (id),
                            CONSTRAINT fk_session_user_id FOREIGN KEY (user_id)
                              REFERENCES users(id)
                              ON DELETE CASCADE
);