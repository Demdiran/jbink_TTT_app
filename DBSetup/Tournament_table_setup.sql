use ttt_db;
create table `tournaments`(
`tournament_ID`			int unsigned not null auto_increment,
`tournament_name`		varchar(50),
`club`					varchar(50),
`date`					datetime,
`adress_ID`				int,
`max_participants`		int unsigned,
primary key(`tournament_ID`),
constraint tournaments_adresses foreign key(`adress_ID`) references adresses (`adress_ID`)
);