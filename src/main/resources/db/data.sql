-- Truncate both tables to reset data
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE wallets CASCADE;

-- Insert into wallets table
INSERT INTO wallets (id, balance, time_created)
VALUES
(100, 1000, '2024-10-22 05:48:59.598160'),
(102, 5000, '2024-10-22 05:48:59.598160'),
(103, 5000, '2024-10-22 05:48:59.598160');

-- Insert into users table
-- Note: Passwords are pre-hashed.
INSERT INTO users (id, first_name, last_name, email, phone_number, password, wallet_id, time_created)
VALUES
(100, 'John', 'Doe', 'john.doe@example.com', '1234567890', '$2a$10$5nLrNZKuivUqJ3ZXrakqUewlC2.l8ti0nKT6Dgqw9KfAznlNWKhHS', 100, '2024-10-22 05:48:59.598160'),
(102, 'Jane', 'Smith', 'jane.smith@example.com', '0987654321', '$2a$10$ebYBX2tefMg6SldYDtnZW.jO5GMTDcazsPrle2X/od5dwB28omfsO', 102, '2024-10-22 05:48:59.598160'),
(103, 'Moh', 'Baba', 'mohbaba@email.com', '08155531802', '$2a$10$5nLrNZKuivUqJ3ZXrakqUewlC2.l8ti0nKT6Dgqw9KfAznlNWKhHS', 103, '2024-10-22 05:48:59.598160');

-- Insert into transactions table
INSERT INTO transactions (id, amount, reference, type, status, wallet_id, created_at, updated_at)
VALUES
(100, 250.50, 'qfyd18kdxb', 1, 1, 100, '2024-10-30 09:15:30', '2024-10-30 09:15:30'),
(101, 500.75, 'qfyd18kdxb', 2, 2, 102, '2024-10-29 10:00:00', '2024-10-29 10:00:00'),
(102, 150.00, 'qfyd18kdxb', 1, 3, 103, '2024-10-28 14:45:15', '2024-10-28 14:45:15'),
(103, 300.25, 'qfyd18kdxb', 2, 1, 103, '2024-10-27 16:30:00', '2024-10-27 16:30:00'),
(104, 450.25, 'qfyd18kdxb', 2, 1, 103, '2024-10-27 16:30:00', '2024-10-27 16:30:00');
