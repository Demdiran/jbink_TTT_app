use ttt_db;
drop table `spelers`;
CREATE TABLE `spelers`(
`speler_ID` 			int unsigned NOT NULL auto_increment,
`speler_naam`			varchar(50) NOT NULL,
`speler_rating`			int unsigned NOT NULL default(1000),
`speler_adres`					varchar(50),
primary key(`speler_ID`)
)