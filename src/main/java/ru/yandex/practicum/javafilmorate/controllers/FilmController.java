package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int generatedId = 1;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Добавление нового фильма: {}", film);
        film.setId(generatedId++);
        log.info("Фильм успешно добавлен: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable int id, @Valid @RequestBody Film updatedFilm) {
        if (films.containsKey(id)) {
            log.info("Обновление фильма");
            updatedFilm.setId(id);
            films.put(updatedFilm.getId(), updatedFilm);
            return updatedFilm;
        } else {
            log.warn("Валидация фильма не прошла");
            throw new NotFoundException("Фильм не найден");
        }
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получение всех фильмов");
        return new ArrayList<>(films.values());
    }
}