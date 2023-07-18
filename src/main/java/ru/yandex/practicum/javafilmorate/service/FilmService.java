package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.inmemory.InMemoryFilmStorage;
import ru.yandex.practicum.javafilmorate.storage.inmemory.InMemoryUserStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;
    private final LocalDate firstFilmRelease = LocalDate.of(1895, Month.DECEMBER, 28);

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void createFilm(Film film) {
        filmValidation(film);
        inMemoryFilmStorage.createFilm(film);
        log.debug("Фильм добавлен: {}", film);
    }

    public void updateFilm(Film film) {
        if (inMemoryFilmStorage.getFilm(film.getId()) == null) {
            String msg = "Фильм с таким ID не существует";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        filmValidation(film);
        inMemoryFilmStorage.updateFilm(film);
        log.debug("Фильм обновлён");
    }

    public List<Film> getAllFilms() {
        log.debug("Получение всех фильмов");
        return inMemoryFilmStorage.getFilms();
    }

    public Film getFilm(Integer filmID) {
        if (inMemoryFilmStorage.getFilm(filmID) == null) {
            String msg = "Фильм с таким ID не существует";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        log.debug("Получение фильма по ID");
        return inMemoryFilmStorage.getFilm(filmID);
    }

    public void addLike(Integer filmId, Integer userId) {
        if (inMemoryFilmStorage.getFilm(filmId) == null) {
            String msg = "Фильм с таким ID не существует";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (inMemoryUserStorage.getUser(userId) == null) {
            String msg = "Пользователя с таким ID не существует";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        inMemoryFilmStorage.addLike(filmId, userId);
        log.debug("Лайк добавлен");
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (inMemoryFilmStorage.getFilm(filmId) == null) {
            String msg = "Фильм с таким ID не существует";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (inMemoryUserStorage.getUser(userId) == null) {
            String msg = "Пользователя с таким ID не существует";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        inMemoryFilmStorage.deleteLike(filmId, userId);
        log.debug("Лайк удалён");
    }

    public List<Film> getTopFilms(int count) {
        log.debug("Получение популярных фильмов");
        return inMemoryFilmStorage.getTopFilms(count);
    }

    public void filmValidation(Film film) {
        if (film.getDuration() <= 0) {
            String msg = "Неправильная продолжительность фильма";
            log.warn(msg);
            throw new ValidationException(msg);
        }

        if (film.getReleaseDate().isBefore(firstFilmRelease)) {
            String msg = "Неверная дата релиза фильма";
            log.warn(msg);
            throw new ValidationException(msg);
        }
    }
}
