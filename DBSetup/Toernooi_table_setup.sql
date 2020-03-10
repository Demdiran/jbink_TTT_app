use ttt_db;
drop table ttt_db;
create table `toernooien`(
`toernooi_ID`			int unsigned not null auto_increment,
`toernooi_naam`			varchar(50),
`datum`					datetime,
`adres`					varchar(50),
`max_deelnemers`		int unsigned,
primary key(`toernooi_ID`)
)