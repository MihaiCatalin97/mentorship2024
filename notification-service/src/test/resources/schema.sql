DROP TABLE IF EXISTS notifications_channels;
DROP TABLE IF EXISTS notifications;

CREATE TABLE notifications
(
    id             INT GENERATED ALWAYS AS IDENTITY,
    user_id        INT       NOT NULL,
    email          VARCHAR(255),
    marked_as_read BOOLEAN DEFAULT FALSE,
    created_at     TIMESTAMP NOT NULL,
    payload        VARCHAR(255),
    type           VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE notifications_channels
(
    id              INT GENERATED ALWAYS AS IDENTITY ,
    notification_id INT          NOT NULL,
    channel         VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT notification_channel
        FOREIGN KEY (notification_id)
            REFERENCES notifications (id) ON DELETE CASCADE
);
