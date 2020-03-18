use ttt_db;
create table `adresses`(
`adress_ID`			int not null auto_increment,
`street`			varchar(50) not null,
`streetnumber`		int not null,
`city`				varchar(50),
`postalcode`		varchar(6),
primary key(`adress_ID`)
);