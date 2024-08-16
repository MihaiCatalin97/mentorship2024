DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS users;

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

-- Test Data

INSERT INTO users VALUES (
                             default,
                             'admin@localhost.com',
                             'First Name',
                             'Last Name',
                             true,
                             upper('8b1a9953c4611296a827abf8c47804d7'), -- Hello
                             'AA-BB-CC'
                         );
INSERT INTO users VALUES (
                             default,
                             'admin2@localhost.com',
                             'First Name',
                             'Last Name',
                             true,
                             upper('8b1a9953c4611296a827abf8c47804d7'), -- Hello
                             'AA-BB-CC'
                         );

INSERT INTO sessions VALUES (
                                default,
                                'sess-key1',
                                now(),
                                1
                            );
INSERT INTO sessions VALUES (
                                default,
                                'sess-key2',
                                now(),
                                1
                            );
INSERT INTO sessions VALUES (
                                default,
                                'sess-key3',
                                now(),
                                2
                            );