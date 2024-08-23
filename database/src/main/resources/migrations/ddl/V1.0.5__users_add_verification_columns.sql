-- Step 1: Remove the 'verified' column
ALTER TABLE users
    DROP COLUMN verified;

-- Step 2: Modify 'verification_token' to allow NULL values
ALTER TABLE users
    ALTER COLUMN verification_token DROP NOT NULL;

-- Step 3: Add new columns 'created_at', 'last_sent_verification_notification', and 'verified_at'
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE users
    ADD COLUMN last_sent_verification_notification TIMESTAMP;

ALTER TABLE users
    ADD COLUMN verified_at TIMESTAMP;

-- Step 4: Update existing records
-- If needed, update 'created_at' with a value that makes sense (like now or based on existing data)
-- You can set the `verified_at` for all users who were previously marked as verified, for example:
-- UPDATE users SET verified_at = CURRENT_TIMESTAMP WHERE verified = TRUE;

-- Update existing records with sensible defaults or derived values
UPDATE users
SET created_at = CURRENT_TIMESTAMP
WHERE created_at IS NULL;

-- Optionally: Set 'verified_at' for users that were previously verified
-- UPDATE users SET verified_at = CURRENT_TIMESTAMP WHERE verified = TRUE;
