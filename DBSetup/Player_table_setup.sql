use ttt_db;
CREATE TABLE `players`(
`player_ID` 			int unsigned NOT NULL auto_increment,
`player_name`			varchar(50),
`player_rating`			int unsigned NOT NULL default(0),
`player_adress`					varchar(50),
primary key(`player_ID`)
);