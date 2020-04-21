drop table if exists cargo;
drop sequence if exists cargo_id_seq;

create sequence cargo_id_seq
  as integer
  maxvalue 2147483647;
alter sequence cargo_id_seq owner to nastya;

create table cargo
(
  id int default nextval('cargo_id_seq')
    constraint cargo_pk
      primary key,
  id_warehouse int,
  id_statement int,
  id_flight int,
  date_in timestamp,
  date_out timestamp,
  kind varchar
);
insert into cargo (id_warehouse, id_statement, id_flight, date_in, date_out, kind) values
(	1,	1,	2,	TIMESTAMP'2019-04-10 15:20:00',	TIMESTAMP'2019-04-14 00:00:00',	'Бытовая техника'),
(	1,	2,	3,	TIMESTAMP'2019-10-01 16:30:00',	TIMESTAMP'2019-10-15 00:00:00',	'Посуда'),
(	2,	3,	4,	TIMESTAMP'2019-08-03 12:00:00',	TIMESTAMP'2019-08-24 00:00:00',	'Мебель'),
(	3,	4,	6,	TIMESTAMP'2018-11-24 16:00:00',	TIMESTAMP'2018-12-05 00:00:00',	'Другое'),
(	4,	5,	8,	TIMESTAMP'2019-07-04 18:00:00',	TIMESTAMP'2019-07-10 00:00:00',	'Бытовая техника'),
(	2,	4,	2,	TIMESTAMP'2019-04-10 15:20:00',	TIMESTAMP'2019-04-14 00:00:00',	'Другое');