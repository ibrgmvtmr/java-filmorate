DROP TABLE IF EXISTS Likes CASCADE;
DROP TABLE IF EXISTS Film_genres CASCADE;
DROP TABLE IF EXISTS Friendships CASCADE;
DROP TABLE IF EXISTS Genres CASCADE;
DROP TABLE IF EXISTS Films CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Mpa CASCADE;


CREATE TABLE IF NOT EXISTS Genres (
    genre_id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Mpa (
    mpa_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS Users (
    user_id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    VARCHAR(200) NOT NULL,
    login    VARCHAR(200) NOT NULL,
    name     VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Films (
    film_id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(200) NOT NULL,
    description  VARCHAR(200) NOT NULL,
    release_date DATE NOT NULL,
    duration     INTEGER NOT NULL,
    mpa_id    INTEGER NOT NULL REFERENCES Mpa (mpa_id)
);

CREATE TABLE IF NOT EXISTS Film_genres (
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id  BIGINT NOT NULL REFERENCES Films (film_id),
    genre_id INTEGER NOT NULL REFERENCES Genres (genre_id)
);

CREATE TABLE IF NOT EXISTS Likes (
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id BIGINT NOT NULL REFERENCES Films (film_id),
    user_id BIGINT NOT NULL REFERENCES Users (user_id)
);

CREATE TABLE IF NOT EXISTS Friendships (
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id  BIGINT NOT NULL REFERENCES Users (user_id),
    friend_id BIGINT NOT NULL REFERENCES Users (user_id)
);
