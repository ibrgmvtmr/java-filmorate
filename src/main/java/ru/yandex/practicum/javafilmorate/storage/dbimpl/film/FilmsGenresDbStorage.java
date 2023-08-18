package ru.yandex.practicum.javafilmorate.storage.dbimpl.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
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
        String sqlQuery = "SELECT G.GENRE_ID, G.NAME\n" +
                "FROM GENRES G\n" +
                "LEFT JOIN FILM_GENRES FG ON FG.GENRE_ID = G.GENRE_ID\n" +
                "WHERE FG.FILM_ID = ?\n" +
                "ORDER BY G.GENRE_ID;";
        try {
            return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
                Genre genre = new Genre();
                genre.setId(rs.getInt("GENRE_ID"));
                genre.setName(rs.getString("NAME"));
                return genre;
            }, filmId);
        } catch (Throwable throwable) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
    }

    @Override
    public void delete(Integer filmId) {
        String sqlQuery = "DELETE FROM FILM_GENRES WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public List<Genre> setFilmGenres(Integer filmId, Set<Genre> genres) {
        genres.forEach((genre) -> create(filmId, genre.getId()));
        return readFilmGenres(filmId);
    }
}
