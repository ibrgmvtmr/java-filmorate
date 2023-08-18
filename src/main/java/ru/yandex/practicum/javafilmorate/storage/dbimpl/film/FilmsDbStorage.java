package ru.yandex.practicum.javafilmorate.storage.dbimpl.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.FilmsStorage;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.FilmsGenresStorage;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.LikesStorage;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.MpaStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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
        KeyHolder generatedId = new GeneratedKeyHolder();
        String sqlQuery = "INSERT INTO Films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)\n" +
                " VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            if (film.getMpa() != null) {
                ps.setInt(5, film.getMpa().getId());
            }
            return ps;
        }, generatedId);

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            List<Genre> genres = filmsGenresStorage.setFilmGenres(film.getId(), new HashSet<>(film.getGenres()));
            film.setGenres(genres);
        }

        if (film.getMpa() != null) {
            film.setMpa(mpaStorage.getMpa(film.getMpa().getId()));
        }

        film.setId(Objects.requireNonNull(generatedId.getKey()).intValue());


        if (film.getMpa() != null) {
            film.setMpa(mpaStorage.getMpa(film.getMpa().getId()));
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (film == null) {
            throw new IllegalArgumentException("Передан недействительный объект фильма для обновления");
        }

        String sqlQuery = "UPDATE FILMS " +
                "SET NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=?, MPA_ID=? " +
                "WHERE FILM_ID=?;";

        int rowsUpdated = jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        if (rowsUpdated == 0) {
            throw new NotFoundException("Фильм с указанным ID не найден, обновление не выполнено");
        }

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            filmsGenresStorage.delete(film.getId());
            List<Genre> genres = filmsGenresStorage.setFilmGenres(film.getId(), new HashSet<>(film.getGenres()));
            film.setGenres(genres);
        }
        return film;
    }


    @Override
    public Film getFilm(Integer filmId) {
        String sqlQuery = "SELECT F.FILM_ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, M.MPA_ID," +
                " M.NAME AS MPA_NAME, M.DESCRIPTION AS MPA_DESCRIPTION\n" +
                "FROM FILMS AS F\n" +
                "JOIN MPA AS M ON F.MPA_ID = M.MPA_ID\n" +
                "WHERE F.FILM_ID = ?;";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> mpaRowToFilm(rs), filmId);
        } catch (Throwable throwable) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
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
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaRowToFilm(rs), count).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Film mpaRowToFilm(ResultSet resultSet) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("FILM_ID"))
                .name(resultSet.getString("NAME"))
                .description((resultSet.getString("DESCRIPTION")))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(mpaStorage.getMpa(resultSet.getInt("MPA_ID")))
                .likes(likesStorage.readUsersLikes(resultSet.getInt("FILM_ID")))
                .genres(filmsGenresStorage.readFilmGenres(resultSet.getInt("FILM_ID")))
                .build();
    }
}
