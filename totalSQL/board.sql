drop table board;

CREATE TABLE board(

boardNo INT PRIMARY KEY AUTO_INCREMENT,
uid VARCHAR(20) NOT NULL,
subject varchar(100) NOT NULL,
content TEXT(20000),
likes INT DEFAULT 0,
interest VARCHAR(100) NULL,
picture VARCHAR(100) NULL,
createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

alter table board drop column date;
alter table board add createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
alter table board add updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;


INSERT INTO board(uid,subject,content,interest) 
VALUES('ssafy','본인의 성장과정을 간략히 작성해주시기 바랍니다',
'[개발자의 미래를 꿈꾸며] 처음 학과~~','it'
);

INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test1','body1','교육업',9);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test2','body2','교육업',8);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test3','body4','교육업',7);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test4','body4','교육업',6);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test5','body5','교육업',5);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test6','body6','교육업',4);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test7','body7','교육업',3);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test8','body8','교육업',2);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test9','body9','교육업',1);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test10','body10','교육업',0);
INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy2','test11','body11','교육업',0);


INSERT INTO board(uid,subject,content,interest,likes) VALUES('ssafy','풀스택 개발자 되기','풀스택 개발자가 되기 위해서 무엇을 해야하는가?','교육업',1);