DROP SCHEMA `VaccineStatusDB`;
CREATE SCHEMA `VaccineStatusDB`;
USE VaccineStatusDB;

CREATE TABLE STATUS(
	StatusID INT NOT NULL,
    Posistion VARCHAR(45),
    
    PRIMARY KEY(StatusID)
);

CREATE TABLE PERSON(
	PersonID INT NOT NULL,
    Name VARCHAR(45),
    Address VARCHAR(45),
    
    PRIMARY KEY(PersonID),
    StatusID INT REFERENCES STATUS(StatusID)
);

CREATE TABLE VACCINATION_HISTORY(
	FirstShot DATE,
    SecondShot DATE,
    BoosterShot DATE,
    
    PersonID INT REFERENCES PERSON(PersonID),
    VaccineID INT REFERENCES VACCINE(VaccineID)
);


CREATE TABLE VACCINE(
	VaccineID INT NOT NULL,
    VaccineName VARCHAR(45),
    
    PRIMARY KEY(VaccineID)
);


CREATE TABLE VACCINE_HAS_HEALTH_CENTER(
    VaccineID INT REFERENCES VACCINE(VaccineID),
    HealthCenterID INT REFERENCES HEALTH_CENTER(HealthCenterID)
);


CREATE TABLE HEALTH_CENTER(
	HealthCenterID INT NOT NULL,
    HealthCenterName VARCHAR(45),
    HealthCenterAddress VARCHAR(45),
    
    PRIMARY KEY(HealthCenterID)
);