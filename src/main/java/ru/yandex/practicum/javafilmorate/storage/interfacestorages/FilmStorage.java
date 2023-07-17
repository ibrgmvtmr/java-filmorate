package ru.yandex.practicum.javafilmorate.storage.interfacestorages;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> getFilms();

    void createFilm(Film film);

    void updateFilm(Film film);

    Film getFilm(Integer id);

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);

    List<Film> getTopFilms(Integer count);

}

