package ru.yandex.practicum.javafilmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<?> createFilm(@RequestBody @Valid Film film) {
        filmService.createFilm(film);
        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    @PutMapping
    public  ResponseEntity<?> updateFilm(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<?> getFilm(@PathVariable Long filmId) {
        return new ResponseEntity<>(filmService.getFilm(filmId), HttpStatus.OK);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public ResponseEntity<?> addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.addLike(filmId, userId);
        return new ResponseEntity<>("Лайк добавлен", HttpStatus.OK);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.deleteLike(filmId, userId);
        return new ResponseEntity<>("Лайк удалён", HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getBestFilms(@RequestParam(defaultValue = "10") String count) {
        return new ResponseEntity<>(filmService.getTopFilms(count), HttpStatus.OK);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFoundException(final IllegalStateException e) {
        return Map.of("Error", e.getMessage());
    }
}