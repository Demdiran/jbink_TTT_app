use ttt_db;
create table `adresses`(
`adress_ID`			int not null auto_increment,
`street`			varchar(50) not null default '',
`streetnumber`		varchar(4) not null default '',
`city`				varchar(50) not null default '',
`postalcode`		varchar(6) not null default '',
primary key(`adress_ID`)
);