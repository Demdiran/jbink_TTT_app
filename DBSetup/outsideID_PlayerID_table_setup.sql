use ttt_db;
create table `outsideID_PlayerID`(
`outside_ID`			varchar(50) not null,
`player_ID`				int unsigned not null,
primary key(`outside_ID`),
foreign key(`player_ID`) references `players` (`player_ID`)
)DEFAULT CHARSET=utf8mb4;