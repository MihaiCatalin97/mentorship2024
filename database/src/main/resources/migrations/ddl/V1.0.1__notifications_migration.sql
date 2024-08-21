-- To be deleted once a real migration is created

CREATE TABLE notifications
(
    id             INT GENERATED ALWAYS AS IDENTITY,
    user_id        INT                      NOT NULL,
    email          TEXT,
    marked_as_read BOOLEAN DEFAULT FALSE,
    created_at     TIMESTAMP NOT NULL,
    payload        TEXT,
    type           VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE notifications_channels (
                                        notification_id INT NOT NULL,
                                        channel         VARCHAR(20) NOT NULL,
                                        FOREIGN KEY(notification_id)
                                            REFERENCES notifications(id),
                                        UNIQUE (notification_id, channel)
);
