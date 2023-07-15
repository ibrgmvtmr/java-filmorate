package ru.yandex.practicum.javafilmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.FilmStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final FilmStorage filmStorage;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmStorage.updateFilm(film);
    }

    @GetMapping()
    public List<Film> getFilms() {
        log.info("Поступил запрос на получение списка всех фильмов.");
        return filmStorage.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable String id) {
        log.info("Получен GET-запрос на получение фильма");
        return filmStorage.getFilm(Integer.parseInt(id));
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLke(@PathVariable String filmId, @PathVariable String userId) {
        filmService.addLike(Integer.parseInt(filmId), Integer.parseInt(userId));
    }

    @PutMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable String filmId, @PathVariable String userId) {
        filmService.deleteLike(Integer.parseInt(filmId), Integer.parseInt(userId));
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(@RequestParam(defaultValue = "10") String count) {
        return filmService.getTopFilms(Integer.parseInt(count));
    }
}