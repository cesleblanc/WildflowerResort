Youtube link (includes project setup): https://youtu.be/SZQHkAeC_Dc
Youtube link (no project setup): https://youtu.be/SZQHkAeC_Dc?t=87


1.) Run all queries in sql folder

2.) Compile:
javac -cp lib/connector-8.0.19.jar -d bin/ src/main/java/ser322/*.java

3.a) Run with input args:
java -cp lib/connector-8.0.19.jar:bin/ main/java/ser322/Main URL USERNAME PASSWORD

3.b) Run with args found in credentials.txt:
java -cp lib/connector-8.0.19.jar:bin/ main/java/ser322/Main

4.) Query tables - examples below
SELECT * FROM GUEST WHERE GUESTID = 1
SELECT * FROM RESERVATION WHERE PRICE < 200
SELECT * FROM ROOMTYPE WHERE MAXOCCUPANCY < 10
SELECT * FROM AMENITY WHERE TYPE = 'POOL'