use ttt_db;
create table `games`(
`game_ID`			int unsigned not null auto_increment,
`points_player_1`	int unsigned not null default '0',
`points_player_2`	int unsigned not null default '0',
primary key(`game_ID`)
);