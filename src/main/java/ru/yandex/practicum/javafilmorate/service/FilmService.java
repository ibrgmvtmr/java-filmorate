package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(int filmId, int userId){
        filmStorage.getFilm(filmId).getLikes().add(userId);
    }

    public void deleteLike(int filmId, int userId){
        if (filmStorage.getFilm(filmId).getLikes().contains(userId)) {
            filmStorage.getFilm(filmId).getLikes().remove(userId);
        } else throw new NotFoundException("Пользователь не ставил лайк на данный фильм.");
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getFilms()
                .stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }
}
