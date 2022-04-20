create table passports
(
    id       serial primary key not null,
    name    varchar(2000),
    lastName varchar(2000),
    birthday date,
    series varchar(2000),
    number varchar(2000),
    validityDate date
);

insert into passports (name, lastName, birthday, series, number, validityDate)
values ('ivan', 'ivanov', '2001-01-01', '0001', '000001', '20220601');

