use ttt_db;
create table `match_player_join`(
`match_ID` 				int unsigned not null,
`player_ID` 			int unsigned not null,
primary key(`match_ID`, `player_ID`),
foreign key(`match_ID`) references `matches` (`match_ID`),
foreign key(`player_ID`) references `players` (`player_ID`)
);