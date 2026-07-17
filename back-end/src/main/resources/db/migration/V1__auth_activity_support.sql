IF OBJECT_ID('RefreshToken', 'U') IS NULL
BEGIN
    CREATE TABLE RefreshToken (
        refresh_token_id INT IDENTITY(1,1) PRIMARY KEY,
        user_id INT NOT NULL,
        token NVARCHAR(500) NOT NULL UNIQUE,
        expires_at DATETIME2 NOT NULL,
        is_revoked BIT NOT NULL DEFAULT 0,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        revoked_at DATETIME2 NULL,
        CONSTRAINT FK_RefreshToken_User FOREIGN KEY (user_id) REFERENCES [User](user_id)
    );
END;

IF COL_LENGTH('ActivityLog', 'field_name') IS NULL
BEGIN
    ALTER TABLE ActivityLog ADD field_name NVARCHAR(100) NULL;
END;

IF NOT EXISTS (SELECT 1 FROM [Role] WHERE role_name = 'Admin')
    INSERT INTO [Role] (role_name) VALUES ('Admin');
IF NOT EXISTS (SELECT 1 FROM [Role] WHERE role_name = 'Manager')
    INSERT INTO [Role] (role_name) VALUES ('Manager');
IF NOT EXISTS (SELECT 1 FROM [Role] WHERE role_name = 'TeamMember')
    INSERT INTO [Role] (role_name) VALUES ('TeamMember');

IF NOT EXISTS (SELECT 1 FROM [User] WHERE email = 'manager@gmail.com')
BEGIN
    INSERT INTO [User] (email, password_hash, full_name, phone, avatar_url, is_active, role_id)
    SELECT 'manager@gmail.com',
           '$2a$10$Qb4TGx0g.xqUq9rShA4xju2qS6ySFqYgf3SYYhPX9Ebr3DkmZFKeG',
           'Manager User', NULL, NULL, 1, role_id
    FROM [Role] WHERE role_name = 'Manager';
END;
