drop table if exists room;
drop sequence if exists room_id_seq;

create sequence room_id_seq
  as integer
  maxvalue 2147483647;
alter sequence room_id_seq owner to nastya;
create table room
(
  id int default nextval('room_id_seq')
    constraint room_pk
      primary key,
  room_number int,
  id_hotel int,
  id_trip int,
  id_transaction int
);

insert into room (room_number, id_hotel, id_trip, id_transaction) values
(	1101,	1,	6,	10),
(	1102,	1,	23,	11),
(	1103,	1,	27,	12),
(	1104,	1,	60,	13),
(	1105,	1,	72,	14),
(	2801,	2,	1,	15),
(	2802,	2,	7,	16),
(	2803,	2,	20,	17),
(	2804,	2,	24,	18),
(	2805,	2,	28,	19),
(	2806,	2,	32,	20),
(	2807,	2,	38,	21),
(	2808,	2,	47,	22),
(	2809,	2,	49,	23),
(	2810,	2,	53,	24),
(	2811,	2,	57,	25),
(	2812,	2,	62,	26),
(	2813,	2,	63,	27),
(	2814,	2,	69,	28),
(	2815,	2,	76,	29),
(	36701,	3,	2,	30),
(	36702,	3,	25,	31),
(	36703,	3,	34,	32),
(	36704,	3,	42,	33),
(	1701,	1,	10,	34),
(	1702,	1,	14,	35),
(	1703,	1,	52,	36),
(	1704,	1,	54,	37),
(	1705,	1,	61,	38),
(	2601,	2,	5,	39),
(	2602,	2,	16,	40),
(	2603,	2,	21,	41),
(	2604,	2,	36,	42),
(	2605,	2,	43,	43),
(	2606,	2,	64,	44),
(	2607,	2,	70,	45),
(	2608,	2,	74,	46),
(	3501,	3,	18,	47),
(	3502,	3,	22,	48),
(	3503,	3,	30,	49),
(	3504,	3,	45,	50),
(	3505,	3,	78,	51),
(	3506,	3,	0,	52),
(	3507,	3,	0,	53),
(	3508,	3,	0,	54),
(	3509,	3,	0,	55),
(	3510,	3,	0,	56),
(	5201,	5,	3,	57),
(	5202,	5,	11,	58),
(	5203,	5,	26,	59),
(	5204,	5,	55,	60),
(	5205,	5,	65,	61),
(	5206,	5,	68,	62),
(	5207,	5,	71,	63),
(	5401,	5,	8,	64),
(	5402,	5,	12,	65),
(	5403,	5,	33,	66),
(	5404,	5,	39,	67),
(	5405,	5,	41,	68),
(	5406,	5,	50,	69),
(	5407,	5,	56,	70),
(	5408,	5,	58,	71),
(	5409,	5,	66,	72),
(	5410,	5,	73,	73),
(	4301,	4,	9,	64),
(	4302,	4,	13,	65),
(	4303,	4,	17,	66),
(	4304,	4,	29,	67),
(	4305,	4,	40,	68),
(	4306,	4,	44,	69),
(	4307,	4,	51,	70),
(	4308,	4,	59,	71),
(	4309,	4,	67,	72),
(	4310,	4,	77,	73),
(	44501,	4,	4,	74),
(	44502,	4,	35,	75),
(	44503,	4,	48,	76),
(	4901,	4,	15,	77),
(	4902,	4,	19,	78),
(	4903,	4,	31,	79),
(	4904,	4,	37,	80),
(	4905,	4,	46,	81),
(	4906,	4,	75,	82),
(	4907,	4,	79,	83);