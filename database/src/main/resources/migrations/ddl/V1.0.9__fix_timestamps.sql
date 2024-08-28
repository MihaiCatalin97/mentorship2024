ALTER TABLE notifications DROP COLUMN created_at;
ALTER TABLE notifications ADD COLUMN created_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE sessions DROP COLUMN expires_at;
ALTER TABLE sessions ADD COLUMN expires_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE transactions DROP COLUMN timestamp;
ALTER TABLE transactions ADD COLUMN timestamp TIMESTAMP WITH TIME ZONE;

ALTER TABLE users DROP COLUMN last_changed_password;
ALTER TABLE users DROP COLUMN created_at;
ALTER TABLE users DROP COLUMN verified_at;
ALTER TABLE users ADD COLUMN last_changed_password TIMESTAMP WITH TIME ZONE;
ALTER TABLE users ADD COLUMN created_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE users ADD COLUMN verified_at TIMESTAMP WITH TIME ZONE;
