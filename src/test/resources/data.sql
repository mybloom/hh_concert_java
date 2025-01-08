INSERT INTO users (name) VALUES
('User1'),
('User2'),
('User3'),
('User4'),
('User5'),
('User6'),
('User7'),
('User8'),
('User9'),
('User10'),
('User11');


INSERT INTO point (user_id, balance) VALUES
(1, 0),
(2, 0),
(3, 0);


INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 1, 'ACTIVE', '2025-01-08 10:00:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 2, 'ACTIVE', '2025-01-08 10:10:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440002', 3, 'ACTIVE', '2025-01-08 10:20:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440003', 4, 'ACTIVE', '2025-01-08 10:30:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440004', 5, 'ACTIVE', '2025-01-08 10:40:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440005', 6, 'ACTIVE', '2025-01-08 10:50:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440006', 7, 'ACTIVE', '2025-01-08 11:00:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440007', 8, 'ACTIVE', '2025-01-08 11:10:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440008', 9, 'ACTIVE', '2025-01-08 11:20:00');

INSERT INTO queue_token (token_uuid, user_id, status, running_expired_at)
VALUES ('550e8400-e29b-41d4-a716-446655440009', 10, 'ACTIVE', '2025-01-08 11:30:00');

--