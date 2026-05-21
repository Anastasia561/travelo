-- Table: city
CREATE TABLE city
(
    id         bigserial   NOT NULL,
    name       varchar(50) NOT NULL,
    country_id bigint      NOT NULL,
    CONSTRAINT city_pk PRIMARY KEY (id)
);

-- Table: country
CREATE TABLE country
(
    id   bigserial   NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT country_pk PRIMARY KEY (id)
);

-- Table: customer
CREATE TABLE customer
(
    id             bigint NOT NULL,
    loyalty_points int    NOT NULL,
    CONSTRAINT customer_pk PRIMARY KEY (id)
);

-- Table: destination
CREATE TABLE destination
(
    id          bigserial    NOT NULL,
    name        varchar(50)  NOT NULL,
    description varchar(100) NOT NULL,
    city_id     bigint       NOT NULL,
    CONSTRAINT destination_pk PRIMARY KEY (id)
);

-- Table: discount
CREATE TABLE discount
(
    id                    bigserial     NOT NULL,
    amount                decimal(3, 2) NOT NULL,
    prome_code            varchar(10)   NOT NULL,
    discount_type         varchar(30)   NOT NULL,
    age_group             varchar(20) NULL,
    condition_description varchar(100) NULL,
    CONSTRAINT discount_pk PRIMARY KEY (id)
);

-- Table: limited_discount
CREATE TABLE limited_discount
(
    id         bigint    NOT NULL,
    start_time timestamp NOT NULL,
    end_time   timestamp NOT NULL,
    CONSTRAINT limited_discount_pk PRIMARY KEY (id)
);

-- Table: payment
CREATE TABLE payment
(
    id             bigserial   NOT NULL,
    method         varchar(20) NOT NULL,
    status         varchar(20) NOT NULL,
    transaction_id UUID        NOT NULL,
    reservation_id bigint      NOT NULL,
    CONSTRAINT payment_pk PRIMARY KEY (id)
);

-- Table: person
CREATE TABLE person
(
    id           bigserial    NOT NULL,
    first_name   varchar(50)  NOT NULL,
    last_name    varchar(50)  NOT NULL,
    phone_number varchar(20) NULL,
    email        varchar(100) NOT NULL,
    password     varchar(250) NOT NULL,
    birthdate    date         NOT NULL,
    CONSTRAINT person_pk PRIMARY KEY (id)
);

-- Table: regular_discount
CREATE TABLE regular_discount
(
    id bigint NOT NULL,
    CONSTRAINT regular_discount_pk PRIMARY KEY (id)
);

-- Table: reservation
CREATE TABLE reservation
(
    id                 bigserial      NOT NULL,
    reservation_number UUID           NOT NULL,
    time               timestamp      NOT NULL,
    status             varchar(20)    NOT NULL,
    expires_at         timestamp      NOT NULL,
    total_price        decimal(10, 2) NOT NULL,
    discount_id        bigint         NOT NULL,
    customer_id        bigint         NOT NULL,
    trip_id            bigint         NOT NULL,
    CONSTRAINT reservation_pk PRIMARY KEY (id)
);

-- Table: seat
CREATE TABLE seat
(
    id          bigserial NOT NULL,
    seat_number int       NOT NULL,
    row         int       NOT NULL,
    vehicle_id  bigint    NOT NULL,
    CONSTRAINT seat_pk PRIMARY KEY (id)
);

-- Table: seat_reservation
CREATE TABLE seat_reservation
(
    reservation_id bigint NOT NULL,
    seat_id        bigint NOT NULL,
    CONSTRAINT seat_reservation_pk PRIMARY KEY (reservation_id, seat_id)
);

-- Table: shift
CREATE TABLE shift
(
    id         bigserial NOT NULL,
    start_time timestamp NOT NULL,
    end_time   timestamp NOT NULL,
    staff_id   bigint    NOT NULL,
    vehicle_id bigint    NOT NULL,
    CONSTRAINT shift_pk PRIMARY KEY (id)
);

-- Table: staff
CREATE TABLE staff
(
    id            bigint         NOT NULL,
    hire_date     date           NOT NULL,
    salary        decimal(10, 2) NOT NULL,
    contract_type varchar(30)    NOT NULL,
    CONSTRAINT staff_pk PRIMARY KEY (id)
);

-- Table: trip
CREATE TABLE trip
(
    id                    bigserial      NOT NULL,
    departure_time        timestamp      NOT NULL,
    arrival_time          timestamp      NOT NULL,
    price                 decimal(10, 2) NOT NULL,
    is_cancelled          boolean        NOT NULL,
    vehicle_id            bigint         NOT NULL,
    city_id               bigint         NOT NULL,
    destination_id        bigint         NOT NULL,
    CONSTRAINT trip_pk PRIMARY KEY (id)
);

-- Table: vehicle
CREATE TABLE vehicle
(
    id             bigserial   NOT NULL,
    vehicle_number varchar(20) NOT NULL,
    vehicle_type   varchar(20) NOT NULL,
    max_row        int         NOT NULL,
    row_width      int         NOT NULL,
    CONSTRAINT vehicle_pk PRIMARY KEY (id)
);

CREATE TABLE discount_days
(
    discount_id bigint      NOT NULL,
    day_of_week varchar(20) NOT NULL,
    CONSTRAINT discount_days_fk FOREIGN KEY (discount_id) REFERENCES regular_discount (id)
);

-- Table: refresh_token
CREATE TABLE refresh_token
(
    id         bigserial PRIMARY KEY,
    person_id bigint      NOT NULL,
    token      VARCHAR(512) NOT NULL,
    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (person_id) REFERENCES person (id)
);

-- foreign keys
-- Reference: Reservation_customer (table: reservation)
ALTER TABLE reservation
    ADD CONSTRAINT Reservation_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: Reservation_discount (table: reservation)
ALTER TABLE reservation
    ADD CONSTRAINT Reservation_discount
        FOREIGN KEY (discount_id)
            REFERENCES discount (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: city_country (table: city)
ALTER TABLE city
    ADD CONSTRAINT city_country
        FOREIGN KEY (country_id)
            REFERENCES country (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: customer_person (table: customer)
ALTER TABLE customer
    ADD CONSTRAINT customer_person
        FOREIGN KEY (id)
            REFERENCES person (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: destination_city (table: destination)
ALTER TABLE destination
    ADD CONSTRAINT destination_city
        FOREIGN KEY (city_id)
            REFERENCES city (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: limited_discount_discount (table: limited_discount)
ALTER TABLE limited_discount
    ADD CONSTRAINT limited_discount_discount
        FOREIGN KEY (id)
            REFERENCES discount (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: payment_Reservation (table: payment)
ALTER TABLE payment
    ADD CONSTRAINT payment_Reservation
        FOREIGN KEY (reservation_id)
            REFERENCES reservation (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: regular_discount_discount (table: regular_discount)
ALTER TABLE regular_discount
    ADD CONSTRAINT regular_discount_discount
        FOREIGN KEY (id)
            REFERENCES discount (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: reservation_trip (table: reservation)
ALTER TABLE reservation
    ADD CONSTRAINT reservation_trip
        FOREIGN KEY (trip_id)
            REFERENCES trip (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: seat_reservation_Reservation (table: seat_reservation)
ALTER TABLE seat_reservation
    ADD CONSTRAINT seat_reservation_Reservation
        FOREIGN KEY (reservation_id)
            REFERENCES reservation (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: seat_reservation_seat (table: seat_reservation)
ALTER TABLE seat_reservation
    ADD CONSTRAINT seat_reservation_seat
        FOREIGN KEY (seat_id)
            REFERENCES seat (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: seat_vehicle (table: seat)
ALTER TABLE seat
    ADD CONSTRAINT seat_vehicle
        FOREIGN KEY (vehicle_id)
            REFERENCES vehicle (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: shift_staff (table: shift)
ALTER TABLE shift
    ADD CONSTRAINT shift_staff
        FOREIGN KEY (staff_id)
            REFERENCES staff (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: shift_vehicle (table: shift)
ALTER TABLE shift
    ADD CONSTRAINT shift_vehicle
        FOREIGN KEY (vehicle_id)
            REFERENCES vehicle (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: staff_person (table: staff)
ALTER TABLE staff
    ADD CONSTRAINT staff_person
        FOREIGN KEY (id)
            REFERENCES person (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: trip_city (table: trip)
ALTER TABLE trip
    ADD CONSTRAINT trip_city
        FOREIGN KEY (city_id)
            REFERENCES city (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: trip_destination (table: trip)
ALTER TABLE trip
    ADD CONSTRAINT trip_destination
        FOREIGN KEY (destination_id)
            REFERENCES destination (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- Reference: trip_vehicle (table: trip)
ALTER TABLE trip
    ADD CONSTRAINT trip_vehicle
        FOREIGN KEY (vehicle_id)
            REFERENCES vehicle (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;
