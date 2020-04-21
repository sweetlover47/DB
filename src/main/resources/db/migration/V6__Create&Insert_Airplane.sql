drop table if exists airplane cascade;
drop sequence if exists airplane_id_seq;

create sequence airplane_id_seq
  as integer
  maxvalue 2147483647;
alter sequence airplane_id_seq owner to nastya;

create table airplane
(
  id int default nextval('airplane_id_seq')
    constraint airplane_pk
      primary key,
  seat_count int,
  cargo_weight float,
  volume_weight float,
  is_cargoplane boolean not null
);

INSERT into airplane (seat_count, cargo_weight, volume_weight, is_cargoplane) VALUES
(	50,	'0',	'324',	false),
(	50,	'42.5',	'501',	true),
(	30,	'2.63',	'726.5', true),
(	30,	'60.5',	'590',	true),
(	50,	'0',	'632',	false),
(	50,	'10',	'745',	true),
(	80,	'0',	'548.2',	false),
(	50,	'16.7',	'988.7',	true),
(	50,	'0',	'419.6',	false),
(	0,	'50',	'0',	true);