USE VaccineStatusDB;

INSERT INTO STATUS
	(StatusID, Posistion)
    VALUES
    (0, 'Student'),
    (1, 'Faculty'),
    (2, 'Staff');

INSERT INTO PERSON
	(PersonID, Name, Address, StatusID)
    VALUES
    (1, 'John', '123 Seaseme St', 0),
    (2, 'Smith', '3123 Seth St', 2),
    (3, 'Steven', '4324 Franklin St', 0),
    (4, 'Ben', '101 Grove Street', 1),
    (5, 'Lary', '324 Barnes St', 2),
    (6, 'William', '434 Pearcy St', 0),
    (7, 'Edwardo', '7443 Gateway Ave', 0),
    (8, 'Kevin', '5457 Maple St', 0),
    (9, 'Brain', '2223 Vernon St', 0),
    (10, 'Brandon', '1111 Atlantis St', 0);
    


INSERT INTO VACCINATION_HISTORY
	(FirstShot, SecondShot, BoosterShot, PersonID, VaccineID)
	VALUES
    ('2021-01-01', '2021-02-11', '2021-08-01', 2, 1),
    ('2021-05-01', '2021-06-21', null, 3, 1),
    ('2021-05-01', '2021-06-21', null, 5, 1),
    ('2021-04-11', '2021-03-01', '2021-09-01', 6, 2),
    ('2021-03-01', null, null, 7, 3),
    ('2021-09-01', null, null, 8, 2),
    ('2021-02-21', '2021-03-21', null, 9, 2),
    ('2021-11-01', null, null, 10, 2);

-- 1: Moderna  2: Pfizer 3: Johnson & Johnson 4:...
INSERT INTO VACCINE
	(VaccineID, VaccineName)
    VALUES
    (1, 'Moderna'),
    (2, 'Pfizer'),
    (3, 'Johnson & Johnson');


INSERT INTO VACCINE_HAS_HEALTH_CENTER
    (VaccineID, HealthCenterID)
    VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (2, 2),
    (3, 3),
    (1, 4),
    (3, 4),
    (2, 5);
    
INSERT INTO HEALTH_CENTER
	(HealthCenterID,HealthCenterName, HealthCenterAddress)
    VALUES
    (1, 'Hospital One', '3123 Something'),
    (2, 'Colleges', '1234 School'),
    (3, 'Backyard', '0000 Hidden'),
    (4, 'Hospital Four', '96543 Los Angeles'),
    (5, 'Hospital One', '87326 San Francisco');