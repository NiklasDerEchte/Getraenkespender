CREATE DATABASE IF NOT EXISTS getraenkespender;

CREATE TABLE IF NOT EXISTS CustomDrink(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    description varchar(255),
    volumeCl1 float,
    volumeCl2 float,
    volumeCl3 float,
    volumeCl4 float,
    volumeCl5 float,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Drink(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    description varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Ingredient(
    id int NOT NULL AUTO_INCREMENT,
    ean varchar(255),
    name varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Inventory(
    id int NOT NULL AUTO_INCREMENT,
    position int,
    ean varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Queue(
    id int NOT NULL AUTO_INCREMENT,
    drinkFk int,
    customDrinkFk int,
    status varchar(255),
    PRIMARY KEY(id),
    FOREIGN KEY (customDrinkFk) REFERENCES CustomDrink(id),
    FOREIGN KEY (drinkFk) REFERENCES Drink(id)
);

CREATE TABLE IF NOT EXISTS Recipe(
    id int NOT NULL AUTO_INCREMENT,
    drinkFk int,
    ean varchar(255),
    volumeCl float,
    PRIMARY KEY(id),
    FOREIGN KEY (drinkFk) REFERENCES Drink(id)
);