
CREATE TABLE user(
	uid VARCHAR(30) PRIMARY KEY,
	email VARCHAR(100),
	PASSWORD VARCHAR(30),
	NAME VARCHAR(10),
	phone VARCHAR(20),
	interest1 VARCHAR(20),
	interest2 VARCHAR(20)
);

alter table user add description VARCHAR(1000) DEFAULT "";
alter table user add img VARCHAR(100) DEFAULT "";

INSERT INTO USER VALUES ('ssafy', 'ssafy@naver.com','ssafy12!!', '예지', '010-1111hashtag-2222', 'IT/인터넷', '연구개발/설계');