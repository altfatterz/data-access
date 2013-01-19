DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS reviews;

CREATE TABLE addresses (
  id            IDENTITY,
  street_name   VARCHAR(100) NOT NULL,
  street_number INT          NOT NULL,
  postcode      VARCHAR(10)  NOT NULL,
  city          VARCHAR(50)  NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE restaurants (
  id         IDENTITY,
  name       VARCHAR(100) NOT NULL,
  website    VARCHAR(100) NOT NULL,
  address_id INT          NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_restaurants_id FOREIGN KEY (address_id)
  REFERENCES addresses (id)
);
CREATE UNIQUE INDEX uq_restaurants_name ON restaurants (name);

CREATE TABLE reviews (
  id            IDENTITY,
  restaurant_id INT,
  user          VARCHAR(20)  NOT NULL,
  description   VARCHAR(200) NOT NULL,
  rate          TINYINT      NOT NULL,
  date          DATE         NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_reviews_restaurant_id FOREIGN KEY (restaurant_id)
  REFERENCES restaurants (id)
);

