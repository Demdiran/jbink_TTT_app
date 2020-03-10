use ttt_db;
create table `speler_toernooi`(
`toernooi_ID`		int unsigned not null,
`speler_ID`			int unsigned not null,
primary key(`toernooi_ID`, `speler_ID`),
foreign key (`toernooi_ID`) references `toernooien` (`toernooi_ID`),
foreign key (`speler_ID`) references `spelers` (`speler_ID`)
)