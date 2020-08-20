
DROP TABLE calender;

CREATE TABLE calender(
calenderno INT PRIMARY KEY AUTO_INCREMENT,
uid VARCHAR(10),
title VARCHAR(30),
content VARCHAR(50),
startdate DATETime,
enddate DATETime
);

INSERT into calender(uid,title,content,startdate,enddate) VALUES("ssafy","싸피","자소서 작성","2018-12-11","2019-11-30"); 