drop table if exists child;
drop sequence if exists child_id_seq;

create sequence child_id_seq
  as integer
  maxvalue 2147483647;
alter sequence child_id_seq owner to nastya;
create table child
(
  id int default nextval('child_id_seq')
    constraint child_pk
      primary key,
  id_parent int
    constraint child_tourist_id_fk
      references tourist
);

insert into child (id_parent) values
(	1),
(	1),
(	4),
(	8),
(	10),
(	20);