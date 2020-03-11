use ttt_db;
create table `tournaments`(
`tournament_ID`			int unsigned not null auto_increment,
`tournament_name`		varchar(50),
`date`					datetime,
`adress`				varchar(50),
`max_participants`		int unsigned,
primary key(`tournament_ID`)
);