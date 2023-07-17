package ru.yandex.practicum.javafilmorate.storage.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.interfacestorages.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int generatedId = 1;

    @Override
    public void createFilm(Film film) {
        film.setId(generatedId++);
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
    }

    @Override
    public void updateFilm(Film film) {
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
    }

    @Override
    public Film getFilm(Integer filmId) {
        return films.get(filmId);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        films.get(filmId).getLikes().add(userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        films.get(filmId).getLikes().add(userId);
    }

    @Override
    public List<Film> getTopFilms(Integer count) {
        int maxSize;
        maxSize = Objects.requireNonNullElse(count, 10);
        return getFilms().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(maxSize).collect(Collectors.toList());
    }
}
