package ru.yandex.practicum.javafilmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.FilmStorage;

import java.util.*;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int generatedId = 1;

    @Override
    public Film createFilm(Film film) {
        film.setId(generatedId++);
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
        log.info("Поступил запрос на добавление фильма. Фильм добавлен");
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            film.setLikes(new HashSet<>());
            films.put(film.getId(), film);
            log.info("Поступил запрос на изменения фильма. Фильм изменён.");
            return film;
        } else {
            log.error("Поступил запрос на изменения фильма. Фильм не найден.");
            throw new NotFoundException("Фильм не найден");
        }
    }

    @Override
    public Film getFilm(int id){
        if (films.containsKey(id)) {
            return films.get(id);
        } else throw new NotFoundException("Фильм не найден");
    }

    @Override
    public List<Film> getFilms() {
        log.info("Получение всех фильмов");
        return new ArrayList<>(films.values());
    }
}
