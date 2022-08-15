create table if not exists drugs
(
    id    bigserial primary key not null,
    name  varchar(255) unique   not null,
    price float                 not null,
    count int                   not null
);

create table if not exists patient
(
    id            bigserial primary key not null,
    firstname     varchar(255)          not null,
    lastname      varchar(255)          not null,
    national_code varchar(255) unique   not null
);

create table if not exists prescription
(
    id         bigserial primary key not null,
    patient_id int8 references patient (id),
    status     varchar(30)           not null
);

create table if not exists Prescription_drugs
(
    id              bigserial primary key not null,
    name            varchar(255)          not null,
    count           int                   not null,
    prescription_id int8 references prescription (id)
);

create table if not exists receipt
(
    id              bigserial primary key not null,
    prescription_id int8 references prescription (id),
    price           float                 not null
);

create table admin
(
    id       serial primary key not null,
    password varchar(255)       not null
)