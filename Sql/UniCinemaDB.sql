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

CREATE TABLE purchase (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
date_purchase date,
id_client int,
FOREIGN KEY(id_client) REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE room(
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
n_rows int,
n_seats int
);

CREATE TABLE film (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
title VARCHAR(50),
duration int,
date_publishing date,
genre int,
plot VARCHAR(1000),
cover VARCHAR(100)
);

CREATE TABLE spectacle (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
show_hour time,
show_date date,
id_film int,
FOREIGN KEY(id_film) REFERENCES film(id) ON DELETE CASCADE
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

CREATE TABLE show_room (
id_room INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
id_show int,
FOREIGN KEY (id_show) REFERENCES spectacle(id) ON DELETE CASCADE,
FOREIGN KEY (id_room) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE director (
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
id_film INT,
firstname VARCHAR(30),
lastname VARCHAR(30),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

CREATE TABLE actors (
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
id_film INT,
firstname VARCHAR(30),
lastname VARCHAR(30),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

CREATE TABLE house_production (
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
id_film INT,
name_house VARCHAR(50),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

CREATE TABLE production (
id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
id_film INT,
firstname VARCHAR(30),
lastname VARCHAR(30),
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE 
);

CREATE TABLE review (
id_client int PRIMARY KEY NOT NULL,
id_film int NOT NULL,
caption varchar(500),
stars int,
FOREIGN KEY (id_client) REFERENCES clients(id) ON DELETE CASCADE,
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

INSERT INTO film VALUES 
("01","Venom - La furia di Carnage","97","2021-10-14","1","Dopo aver trovato un corpo ospite nel giornalista investigativo Eddie Brock, il simbionte alieno deve affrontare un nuovo nemico, Carnage, alter ego dell'assassino seriale Cletus Kasady.","void.png"),
("02","No Time To Die","163","2021-09-30","2","James Bond si è ritirato a vita privata e si gode la pensione in Giamaica in compagna dell’amata Madeleine Swann. Ma la tranquillità è interrotta dalla richiesta di aiuto di un vecchio amico della CIA, Felix Leiter. Bond torna quindi a vestire lo smoking, in una missione per salvare uno scienziato scomparso e sventare un complotto mortale. Inseguimenti e combattimenti lo attendono, come di consueto in diverse straordinarie località, tra cui anche le italiane Matera e a Sapri.","void.png"),
("03","La scuola cattolica","106","2021-10-07","3","La Scuola cattolica è un film diretto da Stefano Mordini che racconta la vita tra i banchi di scuola di un istituto religioso maschile nella Roma bene. Qui ci sono tutti i figli della borghesia romana i cui genitori sono convinti che i loro ragazzi crescano fuori dal caos, dalle sommosse e dal fermento degli anni ‘70 per concentrarsi sulla rigida educazione e sul loro futuro professionale. L’idilliaco equilibrio viene messo in discussione dal crimine di cronaca nera italiana: il Massacro del Circeo, avvenuto la notte tra il 29 e 30 settembre 1975 ai danni di due giovani amiche. L’obiettivo del film è scavare nelle mente dei responsabili per arrivare a tanta violenza.","void.png");


INSERT INTO actors VALUES
("01","01","Tom","Hardy"),
("02","01","Riz","Ahmed"),
("03","01","Naomi","Harris"),
("04","01","Stephen","Graham"),
("05","02","Daniel","Craig"),
("06","02","Léa","Seydoux"),
("07","02","Ralph","Fiennes"),
("08","03","Riccardo","Scamarcio"),
("09","03","Benedetta","Porcaroli"),
("10","03","Jasmine","Trinca");

INSERT INTO house_production VALUES
("01","01","Marvel Entertainment"),
("02","01","Sony Pictures Entertainment"),
("03","02","Columbia-Pictures"),
("04","03","Warner Bros");

INSERT INTO production VALUES
("01","01","Tom","Hardy"),
("02","01","Kelly","Marcel"),
("03","01","Avi","Arad"),
("04","01","Matt","Tolmach"),
("05","02","Micheal G.","Wilson"),
("06","02","Barbara","Broccoli"),
("07","03","Roberto","Sessa");

INSERT INTO director VALUES
("01","01","Andy","Serkis"),
("02","02","Cary","Fukunaga"),
("03","03","Stefano","Mordini");

INSERT INTO spectacle VALUES
("01","16:00:00","2021-10-17","01"),
("02","18:00:00","2021-10-17","01"),
("03","20:00:00","2021-10-17","01"),
("04","18:00:00","2021-10-01","02"),
("05","20:30:00","2021-10-01","02"),
("06","17:00:00","2021-10-10","03"),
("08","19:30:00","2021-10-10","03");

INSERT INTO clients VALUES 
("01","a@gmail.com",NULL,"Antonio","Santosuosso","0","1"),
("02","g@gmail.com",NULL,"Gerardo","Leone","0","1"),
("03","m@gmail.com",NULL,"Mario","Lezzi","0","1");

INSERT INTO purchase VALUES 
("01","2021-10-17","01"),
("02","2021-10-01","02"),
("03","2021-10-10","03");

INSERT INTO ticket VALUES 
("01","5.00","20","F","01","01"),
("02","5.00","15","G","04","02"),
("03","7.00","24","D","08","03"),
("04","6.00","16","G","04","02");

INSERT INTO room VALUES
("01","10","200"),
("2","13","260"),
("3","9","180");

INSERT INTO show_room VALUES
("01","01"),
("02","08"),
("03","05");

INSERT INTO review VALUES 
("01","02","Film Eccellente","5");
