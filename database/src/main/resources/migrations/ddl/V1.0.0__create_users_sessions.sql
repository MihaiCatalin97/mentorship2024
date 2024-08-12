-- To be deleted once a real migration is created
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(128) UNIQUE NOT NULL,
                       first_name VARCHAR(128) NOT NULL,
                       last_name VARCHAR(128) NOT NULL,
                       verified BOOLEAN NOT NULL DEFAULT FALSE,
                       hashed_password VARCHAR(128) NOT NULL,
                       verification_token VARCHAR(128) NOT NULL
);

CREATE TABLE sessions (
                          id SERIAL PRIMARY KEY,
                          session_key VARCHAR(128) NOT NULL,
                          expires_at TIMESTAMP NOT NULL,
                          user_id INT NOT NULL,
                          CONSTRAINT fk_session_user_id FOREIGN KEY (user_id)
                              REFERENCES users(id)
                              ON DELETE CASCADE
);

