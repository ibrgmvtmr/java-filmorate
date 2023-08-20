package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.storage.dbimpl.film.GenresDbStorage;

import java.util.Collection;

@Slf4j
@Service
public class GenreService {
    private final GenresDbStorage genreDbStorage;

    public GenreService(GenresDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Genre create(Genre genre) {
       return genreDbStorage.create(genre);
    }


    public Genre getGenre(int id) {
        if (genreDbStorage.getGenre(id) != null) {
            log.info("Получен GET запрос по эндпоинту '/genres/{id}' на получение жанра по id");
            return genreDbStorage.getGenre(id);
        } else {
            throw new NotFoundException(String.format("Жанра с id=%d нет в списке", id));
        }
    }

    public Collection<Genre> getGenres() {
        log.info("Получен GET запрос по эндпоинту '/genres' на получение всех жанров");
        return genreDbStorage.getGenres();
    }
}
