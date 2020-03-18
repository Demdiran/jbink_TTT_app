use ttt_db;
create table `tournament_adress`(
`tournament_ID`				int unsigned not null,
`adress_ID`					int unsigned not null,
primary key(`tournament_ID`, `adress_ID`),
foreign key(`tournament_ID`) references `tournaments`(`tournament_ID`),
foreign key(`adress_ID`) references `adresses`(`adress_ID`)
);