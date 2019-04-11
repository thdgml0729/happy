CREATE TABLE ANSWERBOARD(
	SEQ NUMBER NOT NULL,
	ID VARCHAR2(10) NOT NULL,
	TITLE VARCHAR2(50) NOT NULL,
	CONTENT VARCHAR2(2000) NOT NULL,
	REFER NUMBER NOT NULL,
	STEP NUMBER NOT NULL,
	DEPTH NUMBER NOT NULL,
	READCOUNT NUMBER NOT NULL,
	REGDATE DATE NOT NULL,
	CONSTRAINT ANSWERBOARD_PK PRIMARY KEY(SEQ)
);

CREATE SEQUENCE ANSWERBOARD_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE MEMBERS(
	ID VARCHAR2(10) NOT NULL,
	PW VARCHAR2(25) NOT NULL,
	NAME VARCHAR2(20) NOT NULL,
	AUTH CHAR(1) NOT NULL,
	DELFLAG CHAR(1) NOT NULL,
	REGDATE DATE NOT NULL,
	CONSTRAINT MEMBERS_CK CHECK (AUTH IN('A','U')), --AUTH라는 컬럼에는 A와 U만 들어갈수 있다. 
	CONSTRAINT MEMBERS_PK PRIMARY KEY (ID)
);

ALTER TABLE ANSWERBOARD ADD CONSTRAINT ANSWERBOARD_FK FOREIGN KEY (ID) REFERENCES MEMBERS(ID);

INSERT INTO MEMBERS(ID, PW, NAME, AUTH, DELFLAG, REGDATE)
VALUES('TEST002', 'TEST001', 'TEST', 'U', 'N', SYSDATE);

--MEMBER
-- boardselect
SELECT ID, PW, NAME, AUTH, DELFLAG, REGDATE FROM MEMBERS ORDER BY AUTH, REGDATE;

--SIGNUPMEMBER
INSERT INTO MEMBERS(ID, PW, NAME, AUTH, DELFLAG, REGDATE)
VALUES('TEST002', 'TEST001', 'TEST', 'U', 'N', SYSDATE);

--IDDUPLICATECHECK
--ID체크는 집계함수를 사용하는 것이 좋다. 
SELECT COUNT(ID) ID FROM MEMBERS WHERE ID = 'TEST001';

--LOGINMEMBER
SELECT ID, PW, AUTH, NAME FROM MEMBERS WHERE ID= 'TEST001' AND PW = 'TEST001';


-------------------------------------ANSWERBOARD

--WRITEBOARD
INSERT INTO ANSWERBOARD
(SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG)
VALUES(ANSWERBOARD_SEQ.NEXTVAL, 'TEST002', '9번', '9번',
(SELECT NVL(MAX(REFER),0)+1 FROM ANSWERBOARD), 0, 0, 0, SYSDATE, 'N');--집계함수 사용하고(공집합처리) 그후에 처리 

--REPLYBOARDUP
UPDATE ANSWERBOARD SET STEP = STEP+1
WHERE STEP > (SELECT STEP FROM ANSWERBOARD WHERE SEQ = '12')
AND REFER = (SELECT REFER FROM ANSWERBOARD WHERE SEQ = '12');

--REPLYBOARDIN
INSERT INTO ANSWERBOARD
(SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG)
VALUES(ANSWERBOARD_SEQ.NEXTVAL, 'TEST001', '덧글작성', '내용',
(SELECT REFER FROM ANSWERBOARD WHERE SEQ='12'), 
(SELECT STEP+1 FROM ANSWERBOARD WHERE SEQ = '12'), 
(SELECT "DEPTH"+1 FROM ANSWERBOARD WHERE SEQ ='12'), 0, SYSDATE, 'N');

-- GETONEBOARD
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
FROM ANSWERBOARD
WHERE SEQ = '4'

-- READCOUNTBOARD
UPDATE ANSWERBOARD SET READCOUNT = READCOUNT +1 WHERE SEQ  = '4';

--MODIFYBOARD
UPDATE ANSWERBOARD SET TITLE = '수정', CONTENT ='수정' WHERE SEQ ='4';

--DELFLAGBOARD(여려개의 SEQ 'Y'로 바꾼다.)
UPDATE ANSWERBOARD SET DELFLAG = 'Y' WHERE DELFLAG = 'N' AND SEQ IN ('11','4'); -- MAP으로 던져서 컬렉션사용 

--DELETEBOARDSEL(글을 지우면 댓글까지 삭제)
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
FROM ANSWERBOARD
WHERE REFER = (SELECT REFER FROM ANSWERBOARD WHERE SEQ = '4')
AND STEP > = (SELECT STEP FROM ANSWERBOARD WHERE SEQ ='4')
AND "DEPTH" >= (SELECT "DEPTH" FROM ANSWERBOARD WHERE SEQ='4')
ORDER BY REFER DESC, STEP;

-- DELETEBOARD(MAP으로 처리)
DELETE FROM ANSWERBOARD WHERE SEQ IN (,);

-- USERBOARDLIST
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
FROM ANSWERBOARD
WHERE DELFLAG = 'N'
ORDER BY REFER DESC, STEP;

--ADMINBOARDLIST
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
FROM ANSWERBOARD
ORDER BY REFER DESC, STEP;

--USERBOARDLISTROW
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
FROM 
	(SELECT ROWNUM RNUM, SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
		FROM 
		(SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
			FROM ANSWERBOARD
				WHERE DELFLAG = 'N'
					ORDER BY REFER DESC, STEP))
WHERE RNUM BETWEEN 1 AND 10;

--USERBOARDLISTTOTAL(유저)
SELECT COUNT(*) FROM ANSWERBOARD WHERE DELFLAG='N';

--ADMINBOARDLISTROW
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
FROM 
	(SELECT ROWNUM RNUM, SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
		FROM 
		(SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP, "DEPTH", READCOUNT, REGDATE, DELFLAG
			FROM ANSWERBOARD
					ORDER BY REFER DESC, STEP))
WHERE RNUM BETWEEN 1 AND 10;

--ADMINBOARDLISTTOTAL(관리자)
SELECT COUNT(*) FROM ANSWERBOARD;