use ttt_db;
create table `player_adress`(
`player_ID`			int unsigned not null,
`adress_ID`			int unsigned not null,
primary key (`player_ID`, `adress_ID`),
foreign key (`player_ID`) references `players` (`player_ID`),
foreign key (`adress_ID`) references `adresses` (`adress_ID`)
);