-- drop table DEMO;
-- drop table SYS_ACCOUNT;

create table DEMO (
    A_ID     varchar(40) not null primary key,
    USERNAME varchar(80) not null,
    PASSWORD varchar(80) not null
);

create table SYS_ACCOUNT (
	ID varchar(40) not null primary key, 
	USERNAME varchar(40) not null unique, 
	PASSWORD varchar(80), 
	REAL_NAME varchar(40), 
	EMAIL varchar(40), 
	REGISTRY_DATE timestamp, 
	ENABLED boolean, 
	ACCOUNT_TYPE varchar(40), 
	CATEGORY varchar(40), 
	PASSWORD_LAST_UPDATE_DATE timestamp,
	DESCRIPTION varchar(100)
);

create table SYS_ROLE (
	ID varchar(40) not null primary key, 
	NAME varchar(40) not null unique, 
	ENABLED boolean, 
	ALIAS varchar(100), 
	ROLE_TYPE varchar(40), 
	CATEGORY varchar(40), 
	DESCRIPTION varchar(100)
);

create table SYS_RESOURCE (
	ID varchar(40) not null primary key, 
	NAME varchar(200) not null unique, 
	ALIAS varchar(100), 
	ENABLED boolean, 
	RESOURCE_TYPE varchar(40), 
	CATEGORY varchar(40),
	DESCRIPTION varchar(100)
);

create table SYS_ACCOUNTROLE (
	ACCOUNT_ID varchar(40) not null, 
	ROLE_ID varchar(40) not null,
	primary key (ACCOUNT_ID,ROLE_ID)
);

create table SYS_ROLERESOURCE (
	ROLE_ID varchar(40) not null, 
	RESOURCE_ID varchar(200) not null,
	primary key (ROLE_ID,RESOURCE_ID)
);

INSERT INTO SYS_ACCOUNT(ID,USERNAME,PASSWORD,REAL_NAME,EMAIL,ENABLED) values('1001','test','1','test','test@test.com',1);
INSERT INTO SYS_ACCOUNT(ID,USERNAME,PASSWORD,REAL_NAME,EMAIL,ENABLED) values('1002','test2','1','test2','test@test.com',1);
INSERT INTO SYS_ACCOUNT(ID,USERNAME,PASSWORD,REAL_NAME,EMAIL,ENABLED) values('1003','test3','1','test3','test@test.com',1);
INSERT INTO SYS_ACCOUNT(ID,USERNAME,PASSWORD,REAL_NAME,EMAIL,ENABLED) values('1004','test4','1','test4','test@test.com',1);

INSERT INTO SYS_ROLE(ID,NAME,ENABLED) values('1001','ROLE_test1',1);
INSERT INTO SYS_ROLE(ID,NAME,ENABLED) values('1002','ROLE_test2',1);
INSERT INTO SYS_ROLE(ID,NAME,ENABLED) values('1003','ROLE_test3',1);
INSERT INTO SYS_ROLE(ID,NAME,ENABLED) values('1004','ROLE_test4',1);

INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1001','/',1,'/');
INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1002','/index',1,'/index');
INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1003','/login',1,'/login');
INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1004','/accessdenied',1,'/accessdenied');
INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1005','/j_spring_cas_security_check',1,'/j_spring_cas_security_check');
INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1006','/test1',1,'/test1');
INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1007','/test2/*',1,'/test2/*');
INSERT INTO SYS_RESOURCE(ID,NAME,ENABLED,ALIAS) values('1008','/test3/**',1,'/test3/**');

INSERT INTO SYS_ACCOUNTROLE(ACCOUNT_ID,ROLE_ID) values('1001','1001');
INSERT INTO SYS_ACCOUNTROLE(ACCOUNT_ID,ROLE_ID) values('1002','1002');
INSERT INTO SYS_ACCOUNTROLE(ACCOUNT_ID,ROLE_ID) values('1003','1003');
INSERT INTO SYS_ACCOUNTROLE(ACCOUNT_ID,ROLE_ID) values('1004','1004');
	
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1001');
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1002');
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1003');
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1004');
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1005');
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1006');
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1007');
INSERT INTO SYS_ROLERESOURCE(ROLE_ID, RESOURCE_ID) values('1001','1008');
