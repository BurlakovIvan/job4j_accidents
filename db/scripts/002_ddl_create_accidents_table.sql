CREATE TABLE IF NOT EXISTS accidents(
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  text VARCHAR,
  address VARCHAR,
  type_id INT NOT NULL REFERENCES typeAccidents(id)
);