package ru.yandex.practicum.javafilmorate.storage.db.filmdb;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.List;

public interface FilmsStorage {
    List<Film> getFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilm(Integer id);

    List<Film> getTopFilms(Integer count);
}
