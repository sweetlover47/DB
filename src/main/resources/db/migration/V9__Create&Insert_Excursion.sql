drop table if exists excursion;
drop sequence if exists excursion_id_seq;

create sequence excursion_id_seq
  as integer
  maxvalue 2147483647;
alter sequence excursion_id_seq owner to nastya;
create table excursion
(
  id int default nextval('excursion_id_seq')
    constraint excursion_pk
      primary key,
  id_agency int,
  num_orders int,
  date timestamp,
  id_transaction int,
  title varchar
);

insert into excursion ( id_agency, num_orders, date, id_transaction, title) values
(	1,	10, TIMESTAMP'2019-01-12 15:00:00',	1,	'Поездка в Лувр'),
(	2,	5, TIMESTAMP'2019-04-13 16:00:00',	2,	'Достопримечательности Парижа'),
(	2,	8, TIMESTAMP'2019-10-14 22:30:00',	3,	'Ночная экскурсия по Парижу на автобусе'),
(	3,	9, TIMESTAMP'2019-08-19 10:00:00',	4,	'Версальский дворец'),
(	3,	11, TIMESTAMP'2019-09-01 13:00:00',	5,	'Храм Спаса на Крови в Санкт-Петербурге'),
(	3,	12, TIMESTAMP'2018-11-26 14:20:00',	6,	'Архитектурные особенности культурной столицы России'),
(	1,	14, TIMESTAMP'2018-10-07 18:30:00',	7,	'Вечерняя Москва'),
(	3,	8, TIMESTAMP'2019-07-08 11:00:00',	8,	'Красная Площадь'),
(	2,	3, TIMESTAMP'2017-05-20 14:30:00',	9,	'Достопримечательности города Дрезден');