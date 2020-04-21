drop table if exists passengers;
drop sequence if exists passengers_id_seq;

create sequence passengers_id_seq
  as integer
  maxvalue 2147483647;
alter sequence passengers_id_seq owner to nastya;
create table passengers
(
  id int default nextval('passengers_id_seq')
    constraint passengers_pk
      primary key,
  id_flight int,
  id_tourist int
);

insert into passengers (id_flight, id_tourist) values
(	1,	20),
(	1,	16),
(	1,	6),
(	1,	5),
( 1,	1),
(	2,	20),
(	2,	19),
(	2,	18),
(	2,	15),
(	2,	6),
(	2,	2),
(	2,	0),
(	3,	17),
(	3,	22),
(	3,	19),
(	3,	16),
(	3,	14),
(	3,	12),
(	3,	10),
(	3,	7),
(	3,	4),
(	3,	3),
(	3,	2),
(	4,	21),
(	4,	19),
(	4,	16),
(	4,	15),
(	4,	14),
(	4,	11),
(	4,	10),
(	4,	8),
(	4,	3),
(	4,	2),
(	5,	22),
(	5,	13),
(	5,	12),
(	5,	9),
(	5,	7),
(	5,	5),
(	5,	4),
(	5,	1),
(	6,	21),
(	6,	20),
(	6,	18),
(	6,	12),
(	6,	11),
(	6,	9),
(	6,	8),
(	6,	6),
(	6,	5),
(	6,	4),
(	6,	1),
(	6,	0),
(	7,	16),
(	7,	15),
(	7,	14),
(	7,	3),
(	7,	2),
(	8,	22),
(	8,	20),
(	8,	18),
(	8,	17),
(	8,	16),
(	8,	15),
(	8,	14),
(	8,	13),
(	8,	10),
(	8,	8),
(	8,	7),
(	8,	6),
(	8,	5),
(	8,	2),
(	8,	0),
(	9,	22),
(	9,	21),
(	9,	12),
(	9,	9),
(	9,	7),
(	9,	4),
(	9,	3);