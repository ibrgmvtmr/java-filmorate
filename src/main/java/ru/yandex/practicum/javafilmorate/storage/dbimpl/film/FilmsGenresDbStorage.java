package ru.yandex.practicum.javafilmorate.storage.dbimpl.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.FilmsGenresStorage;

import java.util.List;
import java.util.Set;

@Component
public class FilmsGenresDbStorage implements FilmsGenresStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public FilmsGenresDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Integer filmId, Integer genreId) {
        String sql = "INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES(?, ?)";
        jdbcTemplate.update(sql, filmId, genreId);
    }

    @Override
    public List<Genre> readFilmGenres(Integer filmId) {
        String sqlQuery = "SELECT G.*\n" +
                     "FROM GENRES G\n" +
                     "LEFT JOIN FILM_GENRES FG ON FG.GENRE_ID = G.GENRE_ID\n" +
                     "WHERE FG.FILM_ID = ?\n" +
                     "ORDER BY G.GENRE_ID;";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Genre.class), filmId);
    }

    @Override
    public void delete(Integer filmId) {
        String sqlQuery = "DELETE FROM FILM_GENRES WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public List<Genre> setGenresFilm(Integer filmId, Set<Genre> genres) {
        for (Genre genre : genres) {
            create(filmId, genre.getId());
        }
        return readFilmGenres(filmId);
    }
}
