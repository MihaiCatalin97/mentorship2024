-- To be deleted once a real migration is created

CREATE TABLE test(
	id INT GENERATED ALWAYS AS IDENTITY,
	field VARCHAR(255) NOT NULL,
	PRIMARY KEY(id)
);
