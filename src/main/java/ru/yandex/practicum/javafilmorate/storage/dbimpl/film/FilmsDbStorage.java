package ru.yandex.practicum.javafilmorate.storage.dbimpl.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.FilmsStorage;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.FilmsGenresStorage;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.LikesStorage;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.MpaStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FilmsDbStorage implements FilmsStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private final LikesStorage likesStorage;

    private final FilmsGenresStorage filmsGenresStorage;

    private final MpaStorage mpaStorage;

    public FilmsDbStorage(JdbcTemplate jdbcTemplate,
                          MpaStorage mpaStorage,
                          LikesStorage likesStorage,
                          FilmsGenresStorage filmsGenresStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.likesStorage = likesStorage;
        this.filmsGenresStorage = filmsGenresStorage;
    }

    @Override
    public Film createFilm(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlQuery = "INSERT INTO Films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)\n" +
                " VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sqlQuery, new String[]{"film_id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            if (film.getMpa() != null) {
                ps.setInt(5, film.getMpa().getId());
            }
            return ps;
        }, keyHolder);

        Integer id = (Integer) keyHolder.getKey();
        film.setId(id);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("UPDATE FILMS " +
                "SET NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=?, MPA_ID=? " +
                "WHERE FILM_ID=?;",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        return film;
    }

    @Override
    public Film getFilm(Integer id) {
        String sqlQuery = "SELECT F.FILM_ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, M.MPA_ID," +
                " M.NAME, M.DESCRIPTION\n" +
                "FROM FILMS AS F\n" +
                "JOIN MPA AS M ON F.MPA_ID = M.MPA_ID;";
       return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaRowToFilm(rs), id).stream()
                .findAny().orElse(null);
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "SELECT *\n" +
                          "FROM FILMS;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaRowToFilm(rs)).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Film> getTopFilms(Integer count) {
        String sqlQuery = "SELECT F.*, count(L.USER_ID) RATING\n" +
                          "FROM FILMS F\n" +
                          "LEFT JOIN LIKES L ON L.FILM_ID = F.FILM_ID\n" +
                          "GROUP BY F.FILM_ID\n" +
                          "ORDER BY RATING DESC\n" +
                          "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaRowToFilm(rs)).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Film mpaRowToFilm(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .description((resultSet.getString("DESCRIPTION")))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(mpaStorage.getMpa(resultSet.getInt("FILM_ID")))
                .likes(likesStorage.readUsersLikes(resultSet.getInt("MPA_ID")))
                .genres(filmsGenresStorage.readFilmGenres(resultSet.getInt("FILM_ID")))
                .build();
    }
}
