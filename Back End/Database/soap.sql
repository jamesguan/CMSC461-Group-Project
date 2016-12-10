CREATE TABLE CustomerAgency 
  (`agency_id` INT,
  `agency_name` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `agency_city` VARCHAR(45) NULL,
  `phone_num` INT(10) NULL,
  PRIMARY KEY (`agency_id`)
  );

-- -----------------------------------------------------
-- Table `soap.db`.`Office`
-- -----------------------------------------------------
CREATE TABLE Office 
  (`office_name` VARCHAR(45),
  `office_city` VARCHAR(45) NULL,
  `sqfootage` INT NULL,
  PRIMARY KEY (`office_name`)
  );

-- -----------------------------------------------------
-- Table `soap.db`.`RentalAgreement`
-- -----------------------------------------------------
CREATE TABLE RentalAgreement 
  (
  `rental_id` INT,
  `amount` DECIMAL(15,2) NULL,
  `end_date` VARCHAR(10) NULL,
  `Office_office_name` VARCHAR(45) NULL,
  PRIMARY KEY (`rental_id`)
    FOREIGN KEY (`Office_office_name`)
    REFERENCES `Office` (`office_name`)
  );

-- -----------------------------------------------------
-- Table `soap.db`.`CustomerAgency_has_RentalAgreement`
-- -----------------------------------------------------
CREATE TABLE CustomerAgency_has_RentalAgreement 
(`CustomerAgency_agency_id` INT,
  `RentalAgreement_rental_id` INT,
  PRIMARY KEY (`CustomerAgency_agency_id`, `RentalAgreement_rental_id`),
    FOREIGN KEY (`CustomerAgency_agency_id`)
    REFERENCES `CustomerAgency` (`agency_id`),
    FOREIGN KEY (`RentalAgreement_rental_id`)
    REFERENCES `RentalAgreement` (`rental_id`)
);