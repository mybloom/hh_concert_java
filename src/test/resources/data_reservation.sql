INSERT INTO users (name) VALUES
('User1');

INSERT INTO point (user_id, balance) VALUES
(1, 0),
(2, 0),
(3, 0),
(10, 0);


INSERT INTO Concert (name) VALUES ('Rock Festival');
INSERT INTO Concert (name) VALUES ('Jazz Night');
INSERT INTO Concert (name) VALUES ('Classical Evening');


INSERT INTO Schedule (concert_id, schedule_date) VALUES
(1, '2024-01-05'),
(1, '2024-01-10'),
(1, '2024-01-20'),
(1, '2024-02-05'),
(1, '2024-02-10'),
(1, '2024-02-20'),
(1, '2024-03-05'),
(1, '2024-03-10'),
(1, '2024-03-20'),
(1, '2024-04-05'),
(1, '2024-04-10'),
(1, '2024-04-20'),
(1, '2024-05-05'),
(1, '2024-05-10'),
(1, '2024-05-20'),
(1, '2024-06-05'),
(1, '2024-06-10'),
(1, '2024-06-15'),
(1, '2024-07-05'),
(1, '2024-07-10'),
(1, '2024-07-20'),
(1, '2024-08-05'),
(1, '2024-08-10'),
(1, '2024-08-20'),
(1, '2024-09-05'),
(1, '2024-09-10'),
(1, '2024-09-20'),
(1, '2024-10-05'),
(1, '2024-10-10'),
(1, '2024-10-20');

INSERT INTO Seat (schedule_id, seat_no, price, status)
VALUES
(1, 1, 10000, 'AVAILABLE'),
(1, 2, 10000, 'AVAILABLE'),
(1, 3, 10000, 'AVAILABLE'),
(1, 4, 10000, 'AVAILABLE'),
(1, 5, 10000, 'AVAILABLE'),
(1, 6, 10000, 'AVAILABLE'),
(1, 7, 10000, 'AVAILABLE'),
(1, 8, 10000, 'AVAILABLE'),
(1, 9, 10000, 'AVAILABLE'),
(1, 10, 10000, 'AVAILABLE'),
(1, 11, 10000, 'AVAILABLE'),
(1, 12, 10000, 'AVAILABLE'),
(1, 13, 10000, 'AVAILABLE'),
(1, 14, 10000, 'AVAILABLE'),
(1, 15, 10000, 'AVAILABLE'),
(1, 16, 10000, 'AVAILABLE'),
(1, 17, 10000, 'AVAILABLE'),
(1, 18, 10000, 'AVAILABLE'),
(1, 19, 10000, 'AVAILABLE'),
(1, 20, 10000, 'AVAILABLE'),
(1, 21, 10000, 'AVAILABLE'),
(1, 22, 10000, 'AVAILABLE'),
(1, 23, 10000, 'AVAILABLE'),
(1, 24, 10000, 'AVAILABLE'),
(1, 25, 10000, 'AVAILABLE'),
(1, 26, 10000, 'AVAILABLE'),
(1, 27, 10000, 'AVAILABLE'),
(1, 28, 10000, 'AVAILABLE'),
(1, 29, 10000, 'AVAILABLE'),
(1, 30, 10000, 'AVAILABLE'),
(1, 31, 10000, 'AVAILABLE'),
(1, 32, 10000, 'AVAILABLE'),
(1, 33, 10000, 'AVAILABLE'),
(1, 34, 10000, 'AVAILABLE'),
(1, 35, 10000, 'AVAILABLE'),
(1, 36, 10000, 'AVAILABLE'),
(1, 37, 10000, 'AVAILABLE'),
(1, 38, 10000, 'AVAILABLE'),
(1, 39, 10000, 'AVAILABLE'),
(1, 40, 10000, 'AVAILABLE'),
(1, 41, 10000, 'AVAILABLE'),
(1, 42, 10000, 'AVAILABLE'),
(1, 43, 10000, 'AVAILABLE'),
(1, 44, 10000, 'AVAILABLE'),
(1, 45, 10000, 'AVAILABLE'),
(1, 46, 10000, 'AVAILABLE'),
(1, 47, 10000, 'AVAILABLE'),
(1, 48, 10000, 'AVAILABLE'),
(1, 49, 10000, 'AVAILABLE'),
(1, 50, 10000, 'AVAILABLE');
