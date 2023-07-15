package ru.yandex.practicum.javafilmorate.storage.interfaceStorage;

import org.springframework.context.annotation.Primary;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.List;
@Primary
public interface FilmStorage {
    List<Film> getFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilm(int id);
}

