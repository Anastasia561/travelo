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
VALUES ('John', 'Doe', 'john@example.com', '$2a$10$vLrzSWN6uhaiMxMaiKwG4u77Dzu81A4/V.vL.hU0Ns2Gsz56HnIKG',
        '1990-05-15'),
       ('Jane', 'Smith', 'jane@example.com', '$2a$10$vLrzSWN6uhaiMxMaiKwG4u77Dzu81A4/V.vL.hU0Ns2Gsz56HnIKG',
        '1985-08-20'),
       ('Alice', 'Brown', 'alice@example.com', '$2a$10$vLrzSWN6uhaiMxMaiKwG4u77Dzu81A4/V.vL.hU0Ns2Gsz56HnIKG',
        '1992-11-30'),
       ('Bob', 'Wilson', 'bob@example.com', '$2a$10$vLrzSWN6uhaiMxMaiKwG4u77Dzu81A4/V.vL.hU0Ns2Gsz56HnIKG',
        '1978-03-10'),
       ('Charlie', 'Davis', 'charlie@example.com', '$2a$10$vLrzSWN6uhaiMxMaiKwG4u77Dzu81A4/V.vL.hU0Ns2Gsz56HnIKG',
        '2000-01-01');
-- pass 111

INSERT INTO customer (id, loyalty_points)
VALUES (1, 150),
       (2, 0),
       (3, 500);

INSERT INTO staff (id, hire_date, salary, contract_type)
VALUES (4, '2020-01-01', 3500.00, 'FULL_TIME'),
       (5, '2022-06-15', 3200.00, 'PART_TIME');

INSERT INTO vehicle (vehicle_number, vehicle_type, max_row, row_width)
VALUES ('001', 'COACH_BUS', 10, 4),
       ('002', 'SHUTTLE_BUS', 5, 3),
       ('DF456', 'TRANSIT_BUS', 6, 5),
       ('GG6689', 'COACH_BUS', 8, 5),
       ('Df789', 'SHUTTLE_BUS', 5, 2);


INSERT INTO seat (seat_number, row, vehicle_id)
SELECT s.seat_num,
       r.row_num,
       1
FROM (SELECT 1 AS row_num
      UNION ALL
      SELECT 2
      UNION ALL
      SELECT 3
      UNION ALL
      SELECT 4
      UNION ALL
      SELECT 5
      UNION ALL
      SELECT 6
      UNION ALL
      SELECT 7
      UNION ALL
      SELECT 8
      UNION ALL
      SELECT 9
      UNION ALL
      SELECT 10) r
         CROSS JOIN
     (SELECT 1 AS seat_num UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s;


INSERT INTO seat (seat_number, row, vehicle_id)
SELECT s.seat_num,
       r.row_num,
       2
FROM (SELECT 1 AS row_num
      UNION ALL
      SELECT 2
      UNION ALL
      SELECT 3
      UNION ALL
      SELECT 4
      UNION ALL
      SELECT 5) r
         CROSS JOIN
         (SELECT 1 AS seat_num UNION ALL SELECT 2 UNION ALL SELECT 3) s;


INSERT INTO seat (seat_number, row, vehicle_id)
SELECT s.seat_num,
       r.row_num,
       3
FROM (SELECT 1 AS row_num
      UNION ALL
      SELECT 2
      UNION ALL
      SELECT 3
      UNION ALL
      SELECT 4
      UNION ALL
      SELECT 5
      UNION ALL
      SELECT 6) r
         CROSS JOIN
     (SELECT 1 AS seat_num UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) s;


INSERT INTO seat (seat_number, row, vehicle_id)
SELECT s.seat_num,
       r.row_num,
       4
FROM (SELECT 1 AS row_num
      UNION ALL
      SELECT 2
      UNION ALL
      SELECT 3
      UNION ALL
      SELECT 4
      UNION ALL
      SELECT 5
      UNION ALL
      SELECT 6
      UNION ALL
      SELECT 7
      UNION ALL
      SELECT 8) r
         CROSS JOIN
     (SELECT 1 AS seat_num UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) s;


INSERT INTO seat (seat_number, row, vehicle_id)
SELECT s.seat_num,
       r.row_num,
       5
FROM (SELECT 1 AS row_num
      UNION ALL
      SELECT 2
      UNION ALL
      SELECT 3
      UNION ALL
      SELECT 4
      UNION ALL
      SELECT 5) r
         CROSS JOIN
         (SELECT 1 AS seat_num UNION ALL SELECT 2) s;


INSERT INTO trip (departure_time, arrival_time, price, is_cancelled, vehicle_id,
                  city_id, destination_id)
VALUES ('2026-06-01 08:00:00', '2026-06-01 12:00:00', 45.00, false, 1, 1, 1),
       ('2026-06-01 14:00:00', '2026-06-01 18:00:00', 45.00, false, 1, 2, 2),
       ('2026-06-02 09:00:00', '2026-06-02 15:00:00', 60.00, false, 2, 3, 3),
       ('2026-06-03 10:00:00', '2026-06-03 22:00:00', 120.00, false, 4, 4, 4),
       ('2026-06-04 07:30:00', '2026-06-04 11:30:00', 40.00, false, 3, 5, 5),
       ('2025-06-04 07:30:00', '2025-06-04 11:30:00', 40.00, false, 3, 1, 1),
       ('2026-06-04 07:30:00', '2026-06-04 11:30:00', 40.00, true, 4, 5, 5),
       ('2026-06-04 07:30:00', '2026-06-04 11:30:00', 40.00, false, 5, 2, 1),
       ('2025-06-04 07:30:00', '2025-06-04 11:30:00', 40.00, false, 2, 2, 2),
       ('2025-06-04 07:30:00', '2025-06-04 11:30:00', 40.00, false, 1, 2, 3);

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

INSERT INTO reservation (reservation_number, time, status, expires_at, loyalty_points_used, discount_id, customer_id,
                         trip_id)
VALUES (gen_random_uuid(), '2026-05-20 10:00:00', 'COMPLETED', '2026-05-20 10:10:00', 40, 1, 1, 1),
       (gen_random_uuid(), '2026-05-21 11:00:00', 'PENDING', '2026-05-21 11:10:00', 35, 2, 2, 2),
       (gen_random_uuid(), '2026-05-22 12:00:00', 'COMPLETED', '2026-05-22 12:10:00', 580, 3, 3, 3),
       (gen_random_uuid(), '2026-05-23 09:00:00', 'CANCELLED', '2026-05-23 09:10:00', 120, 1, 1, 4),
       (gen_random_uuid(), '2026-05-24 15:30:00', 'COMPLETED', '2026-05-24 15:40:00', 0, 1, 2, 1);

INSERT INTO seat_reservation (reservation_id, seat_id)
VALUES (1, 1),
       (2, 2),
       (2, 3),
       (3, 41),
       (3, 42),
       (3, 43),
       (3, 44),
       (3, 45),
       (3, 46),
       (3, 47),
       (3, 48),
       (3, 49),
       (3, 50),
       (3, 51),
       (3, 52),
       (3, 53),
       (5, 10);

INSERT
INTO payment (method, status, transaction_id, reservation_id)
VALUES ('CARD', 'COMPLETED', gen_random_uuid(), 1),
       ('CARD', 'PENDING', gen_random_uuid(), 2),
       ('ONLINE', 'COMPLETED', gen_random_uuid(), 3),
       ('CARD', 'COMPLETED', gen_random_uuid(), 4),
       ('CARD', 'COMPLETED', gen_random_uuid(), 5);