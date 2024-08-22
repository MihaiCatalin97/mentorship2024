INSERT INTO notifications (user_id, email, marked_as_read, created_at, payload, type)
                    VALUES (1, 'user@example.com', FALSE, NOW(), '{"firstName":"ffff"}', 'OVER_SPENDING');

INSERT INTO notifications_channels (notification_id, channel)
                    VALUES ( 1, 'WEB');