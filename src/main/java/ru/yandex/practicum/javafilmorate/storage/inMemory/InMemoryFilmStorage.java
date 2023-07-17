package ru.yandex.practicum.javafilmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long generatedId = 1;

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
    public Optional<Film> getFilm(Long filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        films.get(filmId).getLikes().add(userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        films.get(filmId).getLikes().add(userId);
    }

    @Override
    public List<Film> getTopFilms(String count) {
        long maxSize;
        if (count == null) {
            maxSize = 10;
        } else {
            maxSize = Long.parseLong(count);
        }
        return getFilms().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(maxSize).collect(Collectors.toList());
    }
}
