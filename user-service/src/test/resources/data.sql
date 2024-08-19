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