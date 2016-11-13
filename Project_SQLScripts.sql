-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`CustomerAgency`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`CustomerAgency` (
  `agency_id` INT NOT NULL,
  `agency_name` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `agency_city` VARCHAR(45) NULL,
  `phone_num` INT(10) NULL,
  PRIMARY KEY (`agency_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Office` (
  `office_name` VARCHAR(45) NOT NULL,
  `office_city` VARCHAR(45) NULL,
  `sqfootage` INT NULL,
  PRIMARY KEY (`office_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`RentalAgreement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`RentalAgreement` (
  `rental_id` INT NOT NULL,
  `amount` DECIMAL(15,2) NULL,
  `end_date` DATE NULL,
  `Office_office_name` VARCHAR(45) NULL,
  PRIMARY KEY (`rental_id`, `Office_office_name`),
  INDEX `fk_RentalAgreement_Office1_idx` (`Office_office_name` ASC),
  CONSTRAINT `fk_RentalAgreement_Office1`
    FOREIGN KEY (`Office_office_name`)
    REFERENCES `mydb`.`Office` (`office_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CustomerAgency_has_RentalAgreement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`CustomerAgency_has_RentalAgreement` (
  `CustomerAgency_agency_id` INT NULL,
  `RentalAgreement_rental_id` INT NOT NULL,
  PRIMARY KEY (`CustomerAgency_agency_id`, `RentalAgreement_rental_id`),
  INDEX `fk_CustomerAgency_has_RentalAgreement_RentalAgreement1_idx` (`RentalAgreement_rental_id` ASC),
  INDEX `fk_CustomerAgency_has_RentalAgreement_CustomerAgency_idx` (`CustomerAgency_agency_id` ASC),
  CONSTRAINT `fk_CustomerAgency_has_RentalAgreement_CustomerAgency`
    FOREIGN KEY (`CustomerAgency_agency_id`)
    REFERENCES `mydb`.`CustomerAgency` (`agency_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CustomerAgency_has_RentalAgreement_RentalAgreement1`
    FOREIGN KEY (`RentalAgreement_rental_id`)
    REFERENCES `mydb`.`RentalAgreement` (`rental_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
