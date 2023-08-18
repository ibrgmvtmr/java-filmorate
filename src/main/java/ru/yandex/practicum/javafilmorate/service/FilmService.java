package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.*;

import java.util.Collection;

@Slf4j
@Service
public class FilmService {
    private final FilmsStorage filmsStorage;
    private final LikesStorage likesStorage;

    @Autowired
    public FilmService(FilmsStorage filmStorage, LikesStorage likesDao) {
        this.filmsStorage = filmStorage;
        this.likesStorage = likesDao;
    }

    public Film create(Film film) {
        Film newFilm = filmsStorage.createFilm(film);
        log.debug("Фильм с id = {} добавлен в бд", film.getId());
        return newFilm;
    }

    public Film update(Film film) {
      return filmsStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmsStorage.getFilms();
    }

    public Film getFilm(Integer id) {
        if (filmsStorage.getFilm(id) != null) {
            return filmsStorage.getFilm(id);
        }
        throw new NotFoundException("Фильма с указанным id не существует");
    }

    public Collection<Film> getMostPopularFilms(Integer count) {
        log.debug("Получен запрос на получение популярных фильмов");
        return filmsStorage.getTopFilms(count);
    }

    public void addLike(Integer filmId, Integer userId) {
        log.debug("Получен запрос на добавление лайка фильму");
        likesStorage.addLike(filmId, userId);
    }

    public void deleteLike(long filmId, long userId) {
        log.debug("Получен запрос на удаление лайка фильму");
        likesStorage.deleteLike(filmId,userId);
    }

}
