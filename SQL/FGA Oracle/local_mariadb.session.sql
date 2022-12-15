DROP TABLE test_table;

SHOW tables;

SELECT "Hello World";

CREATE TABLE tes1 (
    id INT AUTO_INCREMENT,
    nama VARCHAR(20),
    origin VARCHAR(20),
    PRIMARY KEY (id)
);

SHOW tables;

DESCRIBE tes1;

INSERT INTO tes1 VALUES (
    NULL, "Agus", "origin5"
);

SELECT * FROM tes1;

DELETE FROM tes1 WHERE id = 2;