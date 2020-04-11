
CREATE TABLE DEPT (
 DEPTNO              INTEGER NOT NULL,
 DNAME               VARCHAR(14),
 LOC                 VARCHAR(13),
 PRIMARY KEY (DEPTNO));

INSERT INTO DEPT VALUES (10,'SPORTS','NEW YORK');
INSERT INTO DEPT VALUES (20,'HOME','DALLAS');
INSERT INTO DEPT VALUES (30,'OUTDOOR','CHICAGO');
INSERT INTO DEPT VALUES (40,'CLOTHING','BOSTON');

CREATE TABLE EMP (
 EMPNO               INTEGER NOT NULL,
 ENAME               VARCHAR(10),
 JOB                 VARCHAR(9),
 MGR                 INTEGER,
 SAL                 FLOAT,
 COMM                FLOAT,
 DEPTNO              INTEGER NOT NULL,
 FOREIGN KEY (DEPTNO) REFERENCES DEPT (DEPTNO),
 FOREIGN KEY (MGR) REFERENCES EMP(EMPNO),
 PRIMARY KEY (EMPNO));

INSERT INTO EMP VALUES (7839,'KING','PRESIDENT',NULL, 5000,NULL,10);
INSERT INTO EMP VALUES (7698,'BLAKE','MANAGER',7839,2850,NULL,30);
INSERT INTO EMP VALUES (7782,'CLARK','MANAGER',7839,2450,NULL,10);
INSERT INTO EMP VALUES (7566,'JONES','MANAGER',7839,2975,NULL,20);
INSERT INTO EMP VALUES (7654,'MARTIN','SALESMAN',7698,1250,1400,30);
INSERT INTO EMP VALUES (7499,'ALLEN','SALESMAN',7698,1600,300,30);
INSERT INTO EMP VALUES (7844,'TURNER','SALESMAN',7698,1500,0,30);
INSERT INTO EMP VALUES (7900,'JAMES','CLERK',7698,950,NULL,30);
INSERT INTO EMP VALUES (7521,'WARD','SALESMAN',7698,1250,500,30);
INSERT INTO EMP VALUES (7902,'FORD','ANALYST',7566,3000,NULL,20);
INSERT INTO EMP VALUES (7369,'SMITH','CLERK',7902,800,NULL,20);
INSERT INTO EMP VALUES (7788,'SCOTT','ANALYST',7566,3000,NULL,20);
INSERT INTO EMP VALUES (7876,'ADAMS','CLERK',7788,1100,NULL,20);
INSERT INTO EMP VALUES (7934,'MILLER','CLERK',7782,1300,NULL,10);

CREATE TABLE PRODUCT (
 PRODID              INTEGER,
 PRICE		     FLOAT,
 MADE_BY	     INTEGER NOT NULL,
 DESCRIP             VARCHAR(30),
 PRIMARY KEY (PRODID),
 FOREIGN KEY (MADE_BY) REFERENCES DEPT (DEPTNO));

INSERT INTO PRODUCT VALUES ('60', 39.99, 10, 'ACE TENNIS RACKET I');
INSERT INTO PRODUCT VALUES ('61', 49.99, 10, 'ACE TENNIS RACKET II');
INSERT INTO PRODUCT VALUES ('70', 9.99, 30, 'TENT STAKES');
INSERT INTO PRODUCT VALUES ('71', 24.49, 20, 'BATH TOWELS');
INSERT INTO PRODUCT VALUES ('90', 19.99, 40, 'TENNIS SHIRT');
INSERT INTO PRODUCT VALUES ('80', 70.19, 30, 'HIKING BOOTS');
INSERT INTO PRODUCT VALUES ('63', 8.99, 20, 'WELCOME MAT');
INSERT INTO PRODUCT VALUES ('30', 199.37, 10, 'CALLAWAY IRONS');
INSERT INTO PRODUCT VALUES ('76', 14.99, 30, 'SB ENERGY BAR-6 PACK');
INSERT INTO PRODUCT VALUES ('81', 12.99, 30, 'SB VITA SNACK-6 PACK');

CREATE TABLE CUSTOMER (
 CUSTID              INTEGER NOT NULL,
 PID		     INTEGER NOT NULL,
 NAME                VARCHAR(45),
 QUANTITY            INTEGER,
 FOREIGN KEY (PID) REFERENCES PRODUCT (PRODID),
 PRIMARY KEY (CUSTID));

INSERT INTO CUSTOMER VALUES (96711, 60, 'Joe Sith', 3);
INSERT INTO CUSTOMER VALUES (94061, 61, 'Luke Moonwalker', 1);
INSERT INTO CUSTOMER VALUES (95133, 70, 'Darth Marth', 7);
INSERT INTO CUSTOMER VALUES (97544, 71, 'Yoda Yoga', 1);
