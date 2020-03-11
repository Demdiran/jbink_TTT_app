use ttt_db;
CREATE TABLE `players`(
`player_ID` 			int unsigned NOT NULL auto_increment,
`player_name`			varchar(50) NOT NULL,
`player_rating`			int unsigned NOT NULL default(1000),
`player_adress`					varchar(50),
primary key(`player_ID`)
);
insert into ttt_db (player_name, player_rating) values ('testplayer', 800);