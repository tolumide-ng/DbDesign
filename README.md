### How to Use:
1. Setup [Apache Derby](https://db.apache.org/derby/papers/DerbyTut/install_software.html) locally on your machine
NB: This project users `db-derby-10.17.1.0`
    - Remeber to add Derby to your path:
        ```
        export DERBY_INSTALL=/opt/Apache/db-derby-10.17.1.0-bin
        export PATH=$DERBY_INSTALL/bin:$PATH
        ```
2. To create your derby databse:
```
ij  //(i.e. interactive jdbc) run this on your cmd, this opens derby's interactive SQL tool
// In the Interactive(ij) session run the followind
CONNECT 'jdbc:derby:ijtest;create=true'; // ijtest is the db we want to create
CREATE TABLE T(A int, B varchar(9));
INSERT INTO T(A,B) VALUES(3, 'record3');
DISCONNECT;
CONNECT 'jdbc:derby:ijtest';
SELECT * FROM T;
DISCONECT
EXIT
```