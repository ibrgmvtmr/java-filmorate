package ru.yandex.practicum.javafilmorate.storage.interfaceStorage;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    List<Film> getFilms();

    void createFilm(Film film);

    void updateFilm(Film film);

    Optional<Film> getFilm(int id);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getTopFilms(String count);

}

