INSERT INTO country (name)
VALUES ('Poland'),
       ('Germany'),
       ('France'),
       ('Italy'),
       ('Spain');

INSERT INTO city (name, country_id)
VALUES ('Warsaw', 1),
       ('Berlin', 2),
       ('Paris', 3),
       ('Rome', 4),
       ('Madrid', 5);

INSERT INTO destination (name, description, city_id)
VALUES ('Central Station', 'Main railway hub', 1),
       ('Brandenburg Gate', 'Historic landmark drop-off', 2),
       ('Eiffel Tower North', 'Tourist arrival point', 3),
       ('Termini Plaza', 'City center terminal', 4),
       ('Puerta del Sol', 'Kilometre Zero point', 5);

INSERT INTO person (first_name, last_name, email, password, birthdate)
VALUES ('John', 'Doe', 'john@example.com', 'hash_1', '1990-05-15'),
       ('Jane', 'Smith', 'jane@example.com', 'hash_2', '1985-08-20'),
       ('Alice', 'Brown', 'alice@example.com', 'hash_3', '1992-11-30'),
       ('Bob', 'Wilson', 'bob@example.com', 'hash_4', '1978-03-10'),
       ('Charlie', 'Davis', 'charlie@example.com', 'hash_5', '2000-01-01');

INSERT INTO customer (id, loyalty_points)
VALUES (1, 150),
       (2, 0),
       (3, 500);

INSERT INTO staff (id, hire_date, salary, contract_type)
VALUES (4, '2020-01-01', 3500.00, 'FULL_TIME'),
       (5, '2022-06-15', 3200.00, 'PART_TIME');

INSERT INTO vehicle (vehicle_number, vehicle_type, max_row, row_width)
VALUES ('001', 'BUS', 10, 4),
       ('002', 'BUS', 5, 3),
       ('DF456', 'TRAIN', 10, 4),
       ('GG6689', 'PLAIN', 15, 6),
       ('Df789', 'BUS', 10, 4);

INSERT INTO seat (seat_number, row, is_booked, vehicle_id)
VALUES (1, 1, false, 1),
       (2, 1, false, 1),
       (3, 2, true, 1),
       (1, 1, false, 2),
       (1, 1, false, 3);

INSERT INTO trip (departure_time, arrival_time, price, available_place_count, is_full, is_cancelled, vehicle_id,
                  city_id, destination_id)
VALUES ('2026-06-01 08:00:00', '2026-06-01 12:00:00', 45.00, 40, false, false, 1, 1, 1),
       ('2026-06-01 14:00:00', '2026-06-01 18:00:00', 45.00, 40, false, false, 1, 2, 2),
       ('2026-06-02 09:00:00', '2026-06-02 15:00:00', 60.00, 15, false, false, 2, 3, 3),
       ('2026-06-03 10:00:00', '2026-06-03 22:00:00', 120.00, 60, false, false, 4, 4, 4),
       ('2026-06-04 07:30:00', '2026-06-04 11:30:00', 40.00, 40, false, true, 3, 5, 5);

INSERT INTO shift (start_time, end_time, staff_id, vehicle_id)
VALUES ('2026-06-01 07:00:00', '2026-06-01 13:00:00', 4, 1),
       ('2026-06-01 13:00:00', '2026-06-01 19:00:00', 5, 1),
       ('2026-06-02 08:00:00', '2026-06-02 16:00:00', 4, 2),
       ('2026-06-03 09:00:00', '2026-06-03 23:00:00', 5, 4),
       ('2026-06-04 06:00:00', '2026-06-04 12:00:00', 4, 3);

INSERT INTO discount (amount, prome_code, discount_type, age_group, condition_description)
VALUES (0.10, 'SAVE10', 'SPECIAL_CONDITION_DISCOUNT', null, '10% off for everyone'),
       (0.20, 'STUDENT20', 'AGE_GROUP_DISCOUNT', 'TEENAGER', null),
       (5.00, 'FLAT5', 'AGE_GROUP_DISCOUNT', 'SENIOR', null),
       (0.50, 'FLASH50', 'SPECIAL_CONDITION_DISCOUNT', null, 'Limited flash sale'),
       (0.15, 'WEEKEND', 'SPECIAL_CONDITION_DISCOUNT', null, 'Sunday special');

INSERT INTO regular_discount (id)
VALUES (3),
       (2),
       (5);

INSERT INTO discount_days (discount_id, day_of_week)
VALUES (3, 'SUNDAY'),
       (2, 'WEDNESDAY'),
       (5, 'SUNDAY'),
       (3, 'SATURDAY');

INSERT INTO limited_discount (id, start_time, end_time)
VALUES (1, '2026-05-01 00:00:00', '2026-06-01 00:00:00'),
       (4, '2026-05-01 00:00:00', '2026-06-01 00:00:00');

INSERT INTO reservation (reservation_number, time, status, expires_at, total_price, discount_id, customer_id, trip_id)
VALUES (gen_random_uuid(), '2026-05-20 10:00:00', 'COMPLETED', '2026-05-20 10:10:00', 40.50, 1, 1, 1),
       (gen_random_uuid(), '2026-05-21 11:00:00', 'PENDING', '2026-05-21 11:10:00', 35.00, 2, 2, 2),
       (gen_random_uuid(), '2026-05-22 12:00:00', 'COMPLETED', '2026-05-22 12:10:00', 55.00, 3, 3, 3),
       (gen_random_uuid(), '2026-05-23 09:00:00', 'CANCELLED', '2026-05-23 09:10:00', 120.00, 1, 1, 4),
       (gen_random_uuid(), '2026-05-24 15:30:00', 'COMPLETED', '2026-05-24 15:40:00', 45.00, 1, 2, 1);

INSERT INTO seat_reservation (reservation_id, seat_id)
VALUES (1, 1),
       (1, 2),
       (2, 4),
       (3, 5),
       (5, 3);

INSERT INTO payment (method, status, transaction_id, reservation_id)
VALUES ('CARD', 'COMPLETED', gen_random_uuid(), 1),
       ('CARD', 'PENDING', gen_random_uuid(), 2),
       ('ONLINE', 'COMPLETED', gen_random_uuid(), 3),
       ('CARD', 'COMPLETED', gen_random_uuid(), 4),
       ('CARD', 'COMPLETED', gen_random_uuid(), 5);