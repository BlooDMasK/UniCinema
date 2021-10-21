Drop database if exists cinema;
CREATE DATABASE cinema;
Use cinema;

CREATE TABLE clients(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
email varchar(30),
pswrd varchar(200),
firstname varchar(25),
lastname varchar(25),
registered boolean,
administrator boolean
);

CREATE TABLE spectacle (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
show_hour time,
show_date date
);

CREATE TABLE purchase (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
date_purchase date,
id_client int,
FOREIGN KEY(id_client) REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE ticket(
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
price double,
seat int,
rowletter char,
id_spectacle int,
id_purchase int,
FOREIGN KEY (id_spectacle) REFERENCES spectacle(id) ON DELETE CASCADE,
FOREIGN KEY (id_purchase) REFERENCES purchase(id) ON DELETE CASCADE
);

CREATE TABLE room(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
n_rows int,
n_seats int
);

CREATE TABLE show_room (
id_room INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
id_show int,
FOREIGN KEY (id_show) REFERENCES spectacle(id) ON DELETE CASCADE,
FOREIGN KEY (id_room) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE film (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
title VARCHAR(50),
lenght int,
date_publishing date,
genre int,
plot VARCHAR(500)
);

CREATE TABLE director (
id_film INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
firstname VARCHAR(30),
lastname VARCHAR(30),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

CREATE TABLE cast_film (
id_film INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
firstname VARCHAR(30),
lastname VARCHAR(30),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

CREATE TABLE house_production (
id_film INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
name_house VARCHAR(50),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

CREATE TABLE production (
id_film INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
firstname VARCHAR(30),
lastname VARCHAR(30),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE 
);

