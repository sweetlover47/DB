alter table statement
  add constraint statement_transaction_transaction_id_fk
    foreign key (id_transaction) references trans;

alter table cargo
  add constraint cargo_warehouse_warehouse_id_fk
    foreign key (id_warehouse) references warehouse;

alter table cargo
  add constraint cargo_statement_statement_id_fk
    foreign key (id_statement) references statement;

alter table cargo
  add constraint cargo_airplane_airplane_id_fk
  foreign key (id_flight) references flight;

alter table passengers
  add constraint passengers_flight_flight_id_fk
    foreign key (id_flight) references flight;

alter table flight
  add constraint flight_transaction_transaction_id_fk
    foreign key (id_transaction) references trans;

alter table flight
  add constraint flight_airplane_airplane_id_fk
    foreign key (id_airplane) references airplane;
/*
alter table flight
  add constraint flight_group_group_id_fk
    foreign key (id_group) references groups;*/

alter table excursion
  add constraint excursion_transaction_transaction_id_fk
    foreign key (id_transaction) references trans;

alter table rest_tourist
  add constraint rest_tourist_excursion_excursion_id_fk
    foreign key (id_excursion) references excursion;

alter table rest_tourist
  add constraint rest_tourist_trip_trip_id_fk
    foreign key (id_trip) references trip;
/*
alter table rest_tourist
  add constraint rest_tourist_tourist_tourist_id_fk
    foreign key (id) references tourist;
*/
alter table cargo_tourist
  add constraint cargo_tourist_trip_trip_id_fk
    foreign key (id_trip) references trip;
/*
alter table cargo_tourist
  add constraint cargo_tourist_tourist_tourist_id_fk
    foreign key (id) references tourist;
*/
alter table trip
  add constraint trip_tourist_tourist_id_fk
    foreign key (id_tourist) references tourist;

/*alter table trip
  add constraint trip_group_group_id_fk
    foreign key (id_group) references groups;*/

alter table trip
  add constraint trip_room_hotel_room_id_fk
    foreign key (hotel_room_id) references room;

alter table room
  add constraint room_transaction_transaction_id_fk
    foreign key (id_transaction) references trans;

alter table room
  add constraint room_hotel_hotel_id_fk
    foreign key (id_hotel) references hotel;

alter table child
  add constraint child_toursit_parent_id_fk
    foreign key (id_parent) references tourist;
