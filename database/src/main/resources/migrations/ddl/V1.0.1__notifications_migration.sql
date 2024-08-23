CREATE TABLE notifications
(
    id             INT GENERATED ALWAYS AS IDENTITY,
    user_id        INT NOT NULL,
    email          TEXT NOT NULL,
    marked_as_read BOOLEAN DEFAULT FALSE NOT NULL,
    created_at     TIMESTAMP NOT NULL,
    payload        TEXT NOT NULL,
    type           VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE notifications_channels (
    notification_id INT NOT NULL,
    channel         VARCHAR(20) NOT NULL,
    FOREIGN KEY(notification_id)
        REFERENCES notifications(id),
    UNIQUE (notification_id, channel)
);
