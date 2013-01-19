INSERT INTO addresses (id, street_name, street_number, postcode, city) VALUES
(1000, 'Oudegracht', '279', '3511 PA', 'Utrecht');
INSERT INTO addresses (id, street_name, street_number, postcode, city) VALUES
(1001, 'Oudegracht', '59', '3511 AD', 'Utrecht');
INSERT INTO addresses (id, street_name, street_number, postcode, city) VALUES
(1002, 'Achter Clarenburg', '6', '3511 JJ', 'Utrecht');

INSERT INTO restaurants (id, name, website, address_id) VALUES
(100, 'Pothuys', 'http://www.pothuys.nl', 1000);
INSERT INTO restaurants (id, name, website, address_id) VALUES
(101, 'Le Connaisseur', 'http://www.leconnaisseur.nl', 1001);
INSERT INTO restaurants (id, name, website, address_id) VALUES
(102, 'Cafe Olivier', 'http://www.cafe-olivier.be', 1002);

INSERT INTO reviews (id, restaurant_id, user, description, rate, created) VALUES
(10, 102, 'Thomas', 'Too noisy. Not cheap. Unique decoration/atmosphere', 2, '2013-01-17');
INSERT INTO reviews (id, restaurant_id, user, description, rate, created) VALUES
(11, 102, 'Christian', 'Awesome! High recommended', 3, '2013-01-12');
