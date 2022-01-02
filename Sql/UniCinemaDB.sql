Drop database if exists cinema;
CREATE DATABASE cinema;
Use cinema;

CREATE TABLE customer(
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
id_customer int,
FOREIGN KEY(id_customer) REFERENCES customer(id) ON DELETE CASCADE
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
cover VARCHAR(100),
poster VARCHAR(100)
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
id_room int,
id_show int,
Primary Key(id_room, id_show),
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

CREATE TABLE actor (
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
id_customer int NOT NULL,
id_film int NOT NULL,
title varchar(50),
caption varchar(500),
stars int,
rev_date date,
rev_time time,
Primary Key(id_customer, id_film),
FOREIGN KEY (id_customer) REFERENCES customer(id) ON DELETE CASCADE,
FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);

INSERT INTO film VALUES 
("01","Venom - La furia di Carnage","97","2021-10-14","1","Dopo aver trovato un corpo ospite nel giornalista investigativo Eddie Brock, il simbionte alieno deve affrontare un nuovo nemico, Carnage, alter ego dell'assassino seriale Cletus Kasady.","wallpaper_venom.jpg","poster_venom.jpg"),
("02","No Time To Die","163","2021-09-30","2","Dopo aver lasciato i servizi segreti, Bond si gode una vita tranquilla in Giamaica. Tuttavia, la pace conquistata si rivela di breve durata quando il suo vecchio amico Felix Leiter gli chiede aiuto.","wallpaper_NoTimeToDie.jpg","poster_NoTimeToDie.jpg"),
("03","Avatar","120","2021-10-08","4","L'ex marine Jake Sully è stato reclutato per una missione sul pianeta Pandora con lo scopo di recuperare risorse naturali in esaurimento sulla Terra. Inaspettatamente si ritrova a voler proteggere il mondo magico al quale si sente stranamente legato.", "wallpaper_avatar.jpg","poster_avatar.jpg"),
("04","Inception","130","2021-10-09","5","Dom Cobb possiede una qualifica speciale: è in grado di inserirsi nei sogni altrui per prelevare i segreti nascosti nel più profondo del subconscio. Viene contattato da Saito, un potentissimo industriale giapponese.","wallpaper_inception.jpg","poster_inception.jpg"),
("05","Zlatan","100","2021-11-11","5","Il film parte dai primi anni della vita di Ibra, dai difficili inizi in Svezia fino all'exploit nel calcio. Vengono narrati i momenti e le persone più significative della sua carriera.","wallpaper_zlatan.jpg","poster_zlatan.jpg");


INSERT INTO actor VALUES
("01","01","Tom","Hardy"),
("02","01","Riz","Ahmed"),
("03","01","Naomi","Harris"),
("04","01","Stephen","Graham"),
("05","02","Daniel","Craig"),
("06","02","Léa","Seydoux"),
("07","02","Ralph","Fiennes"),
("08","03","Riccardo","Scamarcio"),
("09","03","Benedetta","Porcaroli"),
("10","03","Jasmine","Trinca"),
("11","05","Granit","Rushiti"),
("12","05","Dominic Bajraktari","Andersson"),
("13","05","Emmanuele","Aita"),
("14","05","Duccio","Camerini"),
("15","05","Gedomir","Glisovic"),
("16","05","Merima","Dizdarevic"),
("17","05","Selma","Mesanovic"),
("18","05","Linda","Haziri"),
("19","05","Hakan","Bengtsson"),
("20","04","Leonardo","Di Caprio");


INSERT INTO house_production VALUES
("01","01","Marvel Entertainment"),
("02","01","Sony Pictures Entertainment"),
("03","02","Columbia-Pictures"),
("04","03","Warner Bros"),
("05","05","B-Reel Films"),
("06","05","Nordisk Film"),
("07","05","Keplerfilm"),
("08","05","Stiftelsen Svenska Filminstitutet"),
("09","04","Warner Bros");

INSERT INTO production VALUES
("01","01","Tom","Hardy"),
("02","01","Kelly","Marcel"),
("03","01","Avi","Arad"),
("04","01","Matt","Tolmach"),
("05","02","Micheal G.","Wilson"),
("06","02","Barbara","Broccoli"),
("07","05","Roberto","Sessa"),
("08","05","Frida","Bargo"),
("09","05","Tina","Bergstrom"),
("10","03","James","Cameron"),
("11","04","Christopher","Nolan");


INSERT INTO director VALUES
("01","01","Andy","Serkis"),
("02","02","Cary","Fukunaga"),
("03","03","Stefano","Mordini"),
("04","05","Jens","Jorgen"),
("05","04","Christopher","Nolan");

INSERT INTO customer VALUES
("01","gleone2000@gmail.com","78feb7296322c966b855fc42df5666a8cbb9640e3ea626e082f72d77fc6e0f2001271e5134fc4f95808bc96773f0782991a804cddc1a150587df56026d40e268","Gerardo","Leone",false,true),
("02","mlezzi@gvkd.com",null,"Mario","Lezzi",false,false),
("03","suosso@gvkd.com",null,"Antonio","Santosuosso",false,false);


INSERT INTO spectacle VALUES
("01","16:00:00","2022-10-17","01"),
("02","18:00:00","2022-10-17","01"),
("03","20:00:00","2022-10-17","01"),
("04","18:00:00","2022-10-01","02"),
("05","20:30:00","2022-10-01","02"),
("06","17:00:00","2022-10-10","03"),
("08","19:30:00","2022-10-10","03"),
("09","19:30:00","2022-11-11","05"),
("10","21:30:00","2022-11-11","05"),
("11","22:30:00","2022-11-11","01"),
("12","23:30:00","2022-11-11","01"),
("13","20:30:00","2022-11-11","01");

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
("03","05"),
("01", "11"),
("01", "12"),
("01", "13");

INSERT INTO review VALUES 
("01","02","Film Eccellente","Un film davvero con i fiocchi, ben prodotto e registrato","5","2021-12-27","01:36:00");
