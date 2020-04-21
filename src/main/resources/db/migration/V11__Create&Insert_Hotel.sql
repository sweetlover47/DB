drop table if exists hotel;
drop sequence if exists hotel_id_seq;

create sequence hotel_id_seq
  as integer
  maxvalue 2147483647;
alter sequence hotel_id_seq owner to nastya;
create table hotel
(
  id int default nextval('hotel_id_seq')
    constraint hotel_pk
      primary key,
  title varchar
);

insert into hotel ( title) values
(	'Тараканий отель'),
(	'Притон мушек'),
(	'Как у бабушки'),
(	'Почти люкс'),
(	'Luxary Hotel');