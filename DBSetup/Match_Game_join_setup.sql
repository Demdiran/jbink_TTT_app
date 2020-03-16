use ttt_DB;
create table `match_game_join`(
`match_ID`			int unsigned not null,
`game_ID` 			int unsigned not null,
primary key(`match_ID`, `game_ID`),
foreign key(`match_ID`) references `matches` (`match_ID`),
foreign key(`game_ID`) references `games` (`game_ID`)
);