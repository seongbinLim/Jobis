DROP TABLE IF EXISTS userlike;

CREATE TABLE userlike(
    likeno INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	boardno INT NOT NULL,
	uid VARCHAR(30) NOT NULL
);