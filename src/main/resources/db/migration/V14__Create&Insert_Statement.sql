drop table if exists statement;
drop sequence if exists statement_id_seq;

create sequence statement_id_seq
  as integer
  maxvalue 2147483647;
alter sequence statement_id_seq owner to nastya;
create table statement
(
  id int default nextval('statement_id_seq')
    constraint statement_pk
      primary key,
  count int,
  weight float,
  cost_wrap float,
  cost_insurance float,
  id_transaction int
);

insert into statement ( count, weight, cost_wrap, cost_insurance, id_transaction) values
(	1,	'40',	'1200',	'3562',	84),
(	5,	'2.63',	'500',	'480',	85),
(	2,	'60.5',	'2400',	'4913',	86),
(	1,	'10',	'1300',	'2400',	87),
(	1,	'16.7',	'350',	'1799',	88);