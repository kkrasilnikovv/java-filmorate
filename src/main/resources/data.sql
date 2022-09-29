insert into MPAA values ( 1,'G' );
insert into MPAA values ( 2,'PG' );
insert into MPAA values ( 3,'PG-13' );
insert into MPAA values ( 4,'R' );
insert into MPAA values ( 5,'NC-17' );
merge into MPAA (mpaa_id, name)
values (1, 'G'),
(2, 'PG'),
(3, 'PG-13'),
(4, 'R'),
(5, 'NC-17');
merge into GENRES (genre_id, name)
values (1, 'Комедия'),
(2, 'Драма'),
(3, 'Мультфильм'),
(4, 'Триллер'),
(5, 'Документальный'),
(6, 'Боевик');

