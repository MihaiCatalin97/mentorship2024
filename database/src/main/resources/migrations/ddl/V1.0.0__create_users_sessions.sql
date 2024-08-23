CREATE TABLE users (
                       id INTEGER GENERATED ALWAYS AS IDENTITY,
                       email VARCHAR(128) UNIQUE NOT NULL,
                       first_name VARCHAR(128) NOT NULL,
                       last_name VARCHAR(128) NOT NULL,
                       verified BOOLEAN NOT NULL DEFAULT FALSE,
                       hashed_password VARCHAR(128) NOT NULL,
                       verification_token VARCHAR(128) NOT NULL,
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
