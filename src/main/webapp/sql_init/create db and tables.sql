--DROP database IF EXISTS adv_lib;
CREATE database if not exists adv_lib default CHARACTER set utf8 ;
use adv_lib;

--users table creation
DROP TABLE IF exists users;
CREATE TABLE If not exists users (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(128) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `enabled` TINYINT NOT NULL,
  `role` ENUM('client', 'admin') NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

  
--authors table creation
DROP TABLE IF exists authors;
CREATE TABLE IF NOT exists authors (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);



--book library table creation
DROP TABLE if exists book_library;
CREATE TABLE IF not exists book_library (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_name` VARCHAR(64) NOT NULL,
  `author_id` INT  DEFAULT 1,
  `file_name` VARCHAR(64) NOT NULL,
  `path_to_file` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `author_id_fk_idx` (`author_id` ASC) VISIBLE,
  CONSTRAINT `author_id_fk`
    FOREIGN KEY (`author_id`)
    REFERENCES `adv_lib`.`authors` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);

--words from dictionary table creation
DROP TABLE if exists dictionary;
CREATE TABLE `adv_lib`.`dictionary` (
  `word` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`word`),
  UNIQUE INDEX `word_UNIQUE` (`word` ASC) VISIBLE);
  
  --oxford3000 table creation
  DROP TABLE if exists oxford3000;
  CREATE TABLE `adv_lib`.`oxford3000` (
  `word` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `word_UNIQUE` (`word` ASC));

INSERT INTO authors (first_name, last_name) VALUES('unknown', 'unknown'); 