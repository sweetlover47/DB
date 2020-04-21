drop table if exists flight;
drop sequence if exists flight_id_seq;

create sequence flight_id_seq
  as integer
  maxvalue 2147483647;
alter sequence flight_id_seq owner to nastya;
create table flight
(
  id int default nextval('flight_id_seq')
    constraint flight_pk
      primary key,
  id_transaction int,
  id_group int,
  date timestamp,
  id_airplane int
);

insert into flight (id_transaction, id_group, date, id_airplane) values
(	89,	1,	TIMESTAMP'2019-07-10 00:00:00.001',	10),
(	90,	2,	TIMESTAMP'2018-12-05 11:10:33.217',	9),
(	91,	3,	TIMESTAMP'2019-04-14 11:10:44.711',	8),
(	92,	4,	TIMESTAMP'2019-09-05 00:00:00',	7),
(	93,	5,	TIMESTAMP'2019-01-14 00:00:00',	6),
(	94,	6,	TIMESTAMP'2019-10-15 00:00:00',	5),
(	95,	7,	TIMESTAMP'2019-08-24 00:00:00',	4),
(	96,	8,	TIMESTAMP'2017-05-22 00:00:00',	3),
(	97,	9,	TIMESTAMP'2018-10-26 00:00:00',	2),
( 98,	10,	TIMESTAMP'2019-09-05 00:00:00',	1);