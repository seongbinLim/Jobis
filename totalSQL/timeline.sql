CREATE TABLE timeline(
	timelineno INT PRIMARY KEY AUTO_INCREMENT,
	uid VARCHAR(10),
	tltype INT,
	tlname VARCHAR(20) 
);


INSERT INTO timeline(uid,tltype,tlname) VALUES('ssafy',0,'삼성');
INSERT INTO timeline(uid,tltype,tlname) VALUES('ssafy',1,'LG');
INSERT INTO timeline(uid,tltype,tlname) VALUES('ssafy',2,'롯데');