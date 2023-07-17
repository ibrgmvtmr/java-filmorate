package ru.yandex.practicum.javafilmorate.storage.interfaceStorage;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    List<Film> getFilms();

    void createFilm(Film film);

    void updateFilm(Film film);

    Optional<Film> getFilm(Long id);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> getTopFilms(Long count);

}

