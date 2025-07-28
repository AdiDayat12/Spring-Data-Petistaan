INSERT INTO pet_table (name, gender, type)
VALUES
('Fluffy', 'F', 'CAT'),
('Luna', 'F', 'CAT'),
('Diggie', 'M', 'BIRD'),
('Kadita', 'F', 'FISH'),
('Popol Kupa', 'M', 'DOG');

INSERT INTO domestic_pet_table (id, date_of_birth)
VALUES
(1, '2018-07-26'),
(2, '2018-07-26'),
(3, '2018-07-26');

INSERT INTO wild_pet_table (id, place_of_birth)
VALUES
(4, 'Land Of Dawn'),
(5, 'Jim Corbett National Park');

INSERT INTO owner_table (first_name, last_name, gender, city, state, mobile_number, email_id, pet_id)
VALUES
('John', 'Doe', 'M', 'Hyderabad', 'Andhra Pradesh', '9009009001', 'john.doe@scaleupindia.com', 1),
('William', 'Ward', 'M', 'Bhubaneswar', 'Odisha', '9009009002', 'william.ward@scaleupindia.com', 2),
('Alice', 'Smith', 'F', 'Chennai', 'Tamil Nadu', '1234567891', 'alice.smith@scaleupindia.com', 3),
('Bob', 'Brown', 'M', 'Mumbai', 'Maharashtra', '9876543212', 'bob.brown@scaleupindia.com', 4),
('Emily', 'Clark', 'F', 'Pune', 'Maharashtra', '9876543214', 'emily.clark@scaleupindia.com', 5);