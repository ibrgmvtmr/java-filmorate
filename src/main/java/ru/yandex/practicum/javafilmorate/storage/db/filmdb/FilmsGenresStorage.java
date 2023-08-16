package ru.yandex.practicum.javafilmorate.storage.db.filmdb;

import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface FilmsGenresStorage {
    void create(Integer filmId, Integer genreId);

    List<Genre> readFilmGenres(Integer filmId);

    void delete(Integer filmId);

    List<Genre> setGenresFilm(Integer filmId, Set<Genre> genres);
}
