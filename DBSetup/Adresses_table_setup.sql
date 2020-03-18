use ttt_db;
create table `adresses` (
`adress_ID` 		int unsigned not null auto_increment,
`street`			varchar(50),
`city`				varchar(50),
`streetnumber`		int unsigned,
`postalcode`		varchar(6),
primary key(`adress_ID`)
)