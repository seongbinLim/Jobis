DROP TABLE tagging;
DROP TABLE hashtag;
DROP TABLE introduction;


CREATE TABLE hashtag(
tagNo INT AUTO_INCREMENT PRIMARY KEY,
uid VARCHAR(20),
tagname VARCHAR(20),
cnt int
);

CREATE TABLE introduction(
introNo INT AUTO_INCREMENT PRIMARY KEY,
uid VARCHAR(10),
company VARCHAR(10),
startdate DATE,
enddate DATE,
subjectNo INT,
subject VARCHAR(1000),
contents TEXT(20000),
limitlength int
);

CREATE TABLE tagging(
taggingNo int PRIMARY KEY AUTO_INCREMENT,
introNo int REFERENCES introduction (introNo), 
tagNo int REFERENCES hashtag (tagNo)
);


