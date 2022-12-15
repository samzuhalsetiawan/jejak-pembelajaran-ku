SHOW tables;
create table continents(
    continent_id int auto_increment,
    name varchar(255) not null,
    primary key(continent_id)
);
create table regions(
    region_id int auto_increment,
    name varchar(100) not null,
    continent_id INT NOT NULL,
    primary key(region_id),
    foreign key(continent_id) references continents(continent_id)
);
create table countries (
    country_id int auto_increment,
    name varchar(50) not null,
    area decimal(10, 2) not null,
    national_day date,
    country_code2 char(2) not null unique,
    country_code3 char(3) not null unique,
    region_id int not null,
    foreign key(region_id) references regions(region_id),
    primary key(country_id)
);
create table country_stats(
    country_id int,
    year int,
    population int,
    gdp decimal(15, 0),
    primary key (country_id, year),
    foreign key(country_id) references countries(country_id)
);
create table languages(
    language_id int auto_increment,
    language varchar(50) not null,
    primary key (language_id)
);
create table country_languages(
    country_id int,
    language_id int,
    official boolean not null,
    primary key (country_id, language_id),
    foreign key(country_id) references countries(country_id),
    foreign key(language_id) references languages(language_id)
);
create table vips(
    vip_id int primary key,
    name varchar(100) not null
);
create table guests(
    guest_id int primary key,
    name varchar(100) not null
);

SHOW databases;
USE nation;
SHOW tables;
SELECT *
FROM continents;
SELECT *
FROM countries;
CREATE TABLE test_natural_join1 (
    id INT,
    nama VARCHAR(10),
    PRIMARY KEY (id)
);

CREATE TABLE test_natural_join2 (
    id INT,
    job VARCHAR(20),
    PRIMARY KEY (id)
);

INSERT INTO test_natural_join1
VALUES (1, "Sam"),
    (2, "Zuhal"),
    (3, "Setiawan");

INSERT INTO test_natural_join2
VALUES (1, "Programmer"),
    (2, "Software Enginer"),
    (3, "Developer");

SELECT nama, job FROM test_natural_join1
NATURAL JOIN test_natural_join2; 

UPDATE test_natural_join2
SET id = 4
WHERE job = "Programmer";

UPDATE test_natural_join2
SET id = 1
WHERE job = "Developer";

UPDATE test_natural_join2
SET id = 3
WHERE job = "Programmer";

SHOW tables;

DROP TABLE test_natural_join1;
DROP TABLE test_natural_join2;