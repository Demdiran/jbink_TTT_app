use ttt_db;
CREATE TABLE `players`(
`player_ID` 			int unsigned NOT NULL auto_increment,
`player_name`			varchar(50),
`player_rating`			int unsigned NOT NULL default(0),
`adress_ID`				int,
primary key(`player_ID`),
constraint players_adresses foreign key(`adress_ID`) references adresses (`adress_ID`)
);