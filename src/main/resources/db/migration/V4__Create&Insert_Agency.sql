drop table if exists agency cascade;
drop sequence if exists agency_id_seq;

create sequence agency_id_seq
  as integer
  maxvalue 2147483647;
alter sequence agency_id_seq owner to nastya;

create table agency
(
  id int default nextval('agency_id_seq'),
  constraint agency_pk
    primary key (id),
  name varchar
);

INSERT INTO agency(name) VALUES
(	'Hello, World'),
(	'GreatAgency'),
(	'Trick & Trip'),
(	'Привет, Мир'),
(	'Турнир'),
('Лучший тур для вас'),
('поТУРции');