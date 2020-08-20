USE jobis;
drop TABLE interestfield;

CREATE TABLE interestField(
	fieldNo INT PRIMARY KEY AUTO_INCREMENT,
	fieldName VARCHAR(20)
);

INSERT INTO interestfield(fieldNo,fieldName) VALUES (1,'서비스업');
INSERT INTO interestField(fieldNo,fieldName) VALUES (2,'제조·화학');
INSERT INTO interestField(fieldNo,fieldName) VALUES (3,'IT·웹·통신');
INSERT INTO interestField(fieldNo,fieldName) VALUES (4,'은행·금융업');
INSERT INTO interestField(fieldNo,fieldName) VALUES (5,'미디어·디자인');
INSERT INTO interestField(fieldNo,fieldName) VALUES (6,'교육업');
INSERT INTO interestField(fieldNo,fieldName) VALUES (7,'의료·제약·복지');
INSERT INTO interestField(fieldNo,fieldName) VALUES (8,'판매·유통');
INSERT INTO interestField(fieldNo,fieldName) VALUES (9,'건설업');
INSERT INTO interestField(fieldNo,fieldName) VALUES (10,'기관·협회');

SELECT * FROM interestField;