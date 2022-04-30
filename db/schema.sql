create table if not exists passports
(
    id       serial primary key not null,
    name    text,
    lastName text,
    birthday date,
    series int,
    number int,
    validityDate date,
    UNIQUE (series, number)
);

-- insert into passports (name, lastName, birthday, series, number, validityDate)
-- values ('ivan', 'ivanov', '2001-01-01', '0001', '000001', '20220601');

