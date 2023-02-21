create table calandardata
(
actualdate date,
day smallint,
month smallint,
year smallint,
dayoftheyear smallint,
weekday varchar(20),
financialquarter smallint,
quarter smallint,
myquarter smallint
);

CREATE TABLE SHAPEGROUPMASTER
(
shapegroupid smallint primary key,
shapegroupname varchar(10) not null,
last_modifiedby smallint,
last_modified_date timestamp
);

CREATE TABLE shapemaster
(
shapeid smallint primary key,
shapename varchar(30) not null,
shapegroupid smallint references SHAPEGROUPMASTER(shapegroupid),
last_modifiedby smallint,
last_modified_date timestamp
);

create table analysispointer
(
analysisptrid int primary key,
"from" numeric(5,2) not null,
"to" numeric(5,2) not null,
pointers varchar(20),
last_modifiedby smallint,
last_modified_date timestamp
);

create table suratpointer
(
suratptrid int primary key,
"from" numeric(5,2) not null,
"to" numeric(5,2) not null,
pointers varchar(20),
last_modifiedby smallint,
last_modified_date timestamp
);

create table customermemo
(
memoid int primary key,
customer varchar(50) not null,
validity varchar(50) not null,
last_modifiedby smallint,
last_modified_date timestamp
);

create table suratPointercost(
suratptrid int primary key,
shapeid smallint references shapemaster(shapeid),
"From" numeric(5,2) not null,
"To" numeric(5,2) not null,
SRTPointer varchar(20),
Con varchar(40),
CertCost int,
last_modifiedby smallint,
last_modified_date timestamp
);

create table RapNetPointers
(
rapnetptrid int primary key,
"from" numeric(5,2) not null,
"to" numeric(5,2) not null,
pointer varchar(20),
last_modifiedby smallint,
last_modified_date timestamp
);

create table colormaster
(
colorid int primary key,
color varchar(10),
colorgroup varchar(8),
last_modifiedby smallint,
last_modified_date timestamp
);

create table claritymaster
(
clarityid int primary key,
clarity varchar(10),
claritygroup varchar(20),
sort_clarity varchar(25),
last_modifiedby smallint,
last_modified_date timestamp
);
