package ru.yandex.practicum.javafilmorate.storage.db.filmdb;

import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.Collection;

public interface GenresStorage {
    Genre create(Genre genre);

    Collection<Genre> getGenres();

    Genre update(Genre genre);

    Genre getGenre(Integer id);
}
