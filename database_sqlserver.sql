-- ExpenseTracker API - SQL Server Database Schema
-- Run this script to initialize the SQL Server database

-- Create Database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'ExpenseDB')
BEGIN
    CREATE DATABASE ExpenseDB;
END;
GO

USE ExpenseDB;
GO

-- Drop existing table if it exists
IF OBJECT_ID('dbo.expenses', 'U') IS NOT NULL
    DROP TABLE dbo.expenses;
GO

-- Create Expenses Table
CREATE TABLE dbo.expenses (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    category NVARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description NVARCHAR(255) NOT NULL,
    [date] DATETIME NOT NULL DEFAULT GETDATE(),
    created_at DATETIME NOT NULL DEFAULT GETDATE()
);

-- Create indexes for performance
CREATE INDEX idx_expenses_category ON dbo.expenses (category);
CREATE INDEX idx_expenses_date ON dbo.expenses ([date] DESC);
CREATE INDEX idx_expenses_created ON dbo.expenses (created_at DESC);
GO

-- Insert Sample Data
BEGIN TRANSACTION;

INSERT INTO dbo.expenses (category, amount, description, [date], created_at)
VALUES
    ('Food', 25.50, 'Lunch at restaurant', GETDATE(), GETDATE()),
    ('Transport', 10.00, 'Taxi fare', GETDATE(), GETDATE()),
    ('Entertainment', 15.00, 'Movie tickets', GETDATE(), GETDATE()),
    ('Utilities', 50.00, 'Internet bill', DATEADD(DAY, -1, GETDATE()), DATEADD(DAY, -1, GETDATE())),
    ('Groceries', 75.99, 'Weekly shopping', DATEADD(DAY, -2, GETDATE()), DATEADD(DAY, -2, GETDATE())),
    ('Health', 45.00, 'Gym membership', DATEADD(DAY, -3, GETDATE()), DATEADD(DAY, -3, GETDATE())),
    ('Food', 12.50, 'Coffee and snacks', DATEADD(DAY, -4, GETDATE()), DATEADD(DAY, -4, GETDATE())),
    ('Entertainment', 20.00, 'Concert tickets', DATEADD(DAY, -5, GETDATE()), DATEADD(DAY, -5, GETDATE()));

COMMIT;
GO

-- Display confirmation
SELECT 'Database setup complete!' as Status;
SELECT COUNT(*) as TotalExpenses FROM dbo.expenses;
SELECT category, COUNT(*) as Count, SUM(amount) as Total FROM dbo.expenses GROUP BY category;
