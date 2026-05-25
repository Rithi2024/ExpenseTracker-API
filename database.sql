-- Create Database
CREATE DATABASE IF NOT EXISTS expense_db;
USE expense_db;

-- Create Expenses Table
CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    date DATETIME NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX (category),
    INDEX (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Data
INSERT INTO expenses (category, amount, description, date) VALUES
('Food', 25.50, 'Lunch at restaurant', NOW()),
('Transport', 10.00, 'Taxi fare', NOW()),
('Entertainment', 15.00, 'Movie tickets', NOW()),
('Utilities', 50.00, 'Internet bill', DATE_SUB(NOW(), INTERVAL 1 DAY));
