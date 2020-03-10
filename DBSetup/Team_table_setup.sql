use ttt_db;
drop table `teams`;
create table `teams`(
`team_ID`		int unsigned not null auto_increment,
`member1_ID` 	int unsigned not null,
`member2_ID`	int unsigned not null,
`adres`			varchar(50),
primary key (`team_ID`)
)