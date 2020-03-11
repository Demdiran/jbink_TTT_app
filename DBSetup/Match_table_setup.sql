use ttt_db;
drop table `match`;
create table `matches`(
`match_ID` 				int unsigned not null auto_increment,
`player1_ID`				int unsigned,
`player2_ID`				int unsigned,
`result`					varchar(50),
primary key(`match_ID`)
)