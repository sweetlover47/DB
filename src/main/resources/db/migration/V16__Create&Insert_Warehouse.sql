drop table if exists warehouse;
drop table if exists transaction;
drop sequence if exists warehouse_id_seq;

create sequence warehouse_id_seq
  as integer
  maxvalue 2147483647;
alter sequence warehouse_id_seq owner to nastya;

create table warehouse
(
  id int default nextval('warehouse_id_seq')
    constraint warehouse_pk
      primary key,
  num_space int
);

insert into warehouse (num_space) values
(10),
(	30),
(	15),
(	35);