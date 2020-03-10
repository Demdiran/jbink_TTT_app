use ttt_db;
drop table `wedstrijden`;
create table `wedstrijden`(
`wedstrijd_ID` 				int unsigned not null auto_increment,
`speler1_ID`				int unsigned,
`speler2_ID`				int unsigned,
`uitslag`					varchar(50),
primary key(`wedstrijd_ID`)
)