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
