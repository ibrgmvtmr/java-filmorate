package ru.yandex.practicum.javafilmorate.storage.dbimpl.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.GenresStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GenresDbStorage implements GenresStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public GenresDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre create(Genre genre) {
        KeyHolder generatedId = new GeneratedKeyHolder();
        String sqlQuery = "INSERT INTO GENRES\n" +
                          "(NAME)\n" +
                          "VALUES(?);";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sqlQuery, new String[]{"GENRE_ID"});
            ps.setString(1, genre.getName());
            return ps;
        }, generatedId);

        genre.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        String sqlQuery = "UPDATE GENRES\n" +
                "SET NAME = ?\n" +
                "WHERE GENRE_ID = ?;";
        jdbcTemplate.update(sqlQuery, genre.getName(), genre.getId());
        return genre;
    }

    @Override
    public Collection<Genre> getGenres() {
        String sqlQuery = "SELECT *\n" +
                          "FROM GENRES;";
       return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowToGenre(rs)).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Genre getGenre(Integer id) {
        String sqlQuery = "SELECT *\n" +
                          "FROM GENRES\n" +
                          "WHERE GENRE_ID = ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowToGenre(rs), id).stream()
                .findAny().orElse(null);
    }

    public Genre mapRowToGenre(ResultSet resultSet) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("GENRE_ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }
}
