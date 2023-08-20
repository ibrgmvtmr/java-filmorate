DELETE FROM mpa;
INSERT INTO MPA (mpa_id, NAME, DESCRIPTION) VALUES (1, 'G', 'У фильма нет возрастных ограничений');
INSERT INTO MPA (mpa_id, NAME, DESCRIPTION) VALUES (2, 'PG', 'Детям рекомендуется смотреть фильм с родителями');
INSERT INTO MPA (mpa_id, NAME, DESCRIPTION) VALUES (3, 'PG-13', 'Детям до 13 лет просмотр не желателен');
INSERT INTO MPA (mpa_id, NAME, DESCRIPTION) VALUES (4, 'R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
INSERT INTO MPA (mpa_id, NAME, DESCRIPTION) VALUES (5, 'NC-17', 'Лицам до 18 лет просмотр запрещён');

INSERT INTO GENRES (GENRE_ID, NAME) VALUES (1, 'Комедия');
INSERT INTO GENRES (GENRE_ID, NAME) VALUES (2, 'Драма');
INSERT INTO GENRES (GENRE_ID, NAME) VALUES (3, 'Мультфильм');
INSERT INTO GENRES (GENRE_ID, NAME) VALUES (4, 'Триллер');
INSERT INTO GENRES (GENRE_ID, NAME) VALUES (5, 'Документальный');
INSERT INTO GENRES (GENRE_ID, NAME) VALUES (6, 'Боевик');

