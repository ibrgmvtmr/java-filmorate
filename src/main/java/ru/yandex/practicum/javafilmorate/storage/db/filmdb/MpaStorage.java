package ru.yandex.practicum.javafilmorate.storage.db.filmdb;

import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {

    Mpa create(Mpa mpa);

    Mpa update(Mpa mpa);

    Mpa getMpa(Integer id);

    List<Mpa> getMpas();

    Mpa getFilmMpa(Integer id);
}
