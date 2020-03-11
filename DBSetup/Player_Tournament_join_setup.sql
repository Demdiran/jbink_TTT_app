use ttt_db;
create table `player_tournament`(
`tournament_ID`		int unsigned not null,
`player_ID`			int unsigned not null,
primary key(`tournament_ID`, `player_ID`),
foreign key (`tournament_ID`) references `tournaments` (`tournament_ID`),
foreign key (`player_ID`) references `players` (`player_ID`)
)