use ttt_db;
create table `teams`(
`team_ID`		int unsigned not null auto_increment,
`member1_ID` 	int unsigned not null,
`member2_ID`	int unsigned not null,
`adress`			varchar(50),
primary key (`team_ID`)
)