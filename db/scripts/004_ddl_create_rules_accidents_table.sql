CREATE TABLE IF NOT EXISTS rulesAccidents(
     id SERIAL PRIMARY KEY,
     accident_id INT NOT NULL REFERENCES accidents(id),
     rule_id INT NOT NULL REFERENCES rulesTable(id)
)