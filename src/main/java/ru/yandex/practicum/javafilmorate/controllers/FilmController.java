package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<?> createFilm(@RequestBody @Valid Film film) {
        filmService.create(film);
        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    @PutMapping
    public  ResponseEntity<?> updateFilm(@Valid @RequestBody Film film) {
        filmService.update(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getFilms() {
        return new ResponseEntity<>(filmService.getFilms(), HttpStatus.OK);
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<?> getFilm(@PathVariable Integer filmId) {
        return new ResponseEntity<>(filmService.getFilm(filmId), HttpStatus.OK);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public ResponseEntity<?> addLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.addLike(filmId, userId);
        return new ResponseEntity<>("Лайк добавлен", HttpStatus.OK);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.deleteLike(filmId, userId);
        return new ResponseEntity<>("Лайк удалён", HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getBestFilms(@RequestParam(defaultValue = "10") int count) {
        return new ResponseEntity<>(filmService.getMostPopularFilms(count), HttpStatus.OK);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFoundException(final IllegalStateException e) {
        return Map.of("Error", e.getMessage());
    }
}