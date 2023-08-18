package ru.yandex.practicum.javafilmorate.storage.dbimpl.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Mpa;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.MpaStorage;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa create(Mpa mpa) {
        String sqlQuery = "INSERT INTO MPA (NAME, DESCRIPTION)\n " +
                          "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, mpa.getName(), mpa.getDescription());
        Integer id = jdbcTemplate.queryForObject("SELECT MAX(MPA_ID) FROM MPA", Integer.class);
        mpa.setId(id);
        return mpa;
    }

    @Override
    public Mpa update(Mpa mpa) {
        String sqlQuery = "UPDATE MPA\n " +
                "SET NAME = ?, DESCRIPTION = ?\n" +
                "WHERE MPA_ID = ?;";
        jdbcTemplate.update(sqlQuery, mpa.getName(), mpa.getDescription(), mpa.getId());
        return mpa;
    }

    @Override
    public Mpa getMpa(Integer id) {
        String sqlQuery = "SELECT MPA_ID, NAME, DESCRIPTION\n" +
                "FROM MPA\n" +
                "WHERE MPA_ID = ?;";
        List<Mpa> result = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowToMpa(rs), id);

        if (result.isEmpty()) {
            throw new NotFoundException("MPA c таким id не существует");
        }

        return result.get(0);
    }

    @Override
    public List<Mpa> getMpas() {
        String sql = "SELECT M.MPA_ID AS ID, M.NAME, M.DESCRIPTION " +
                "FROM MPA M";
        return jdbcTemplate.query(sql,  (rs, rowNum) -> mapRowToMpa(rs)).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Mpa getFilmMpa(Integer id) {
        String sqlQuery = "SELECT M.*\n" +
                "FROM MPA M\n" +
                "LEFT JOIN FILMS F ON F.MPA_ID = M.MPA_ID\n" +
                "WHERE FILM_ID = ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowToMpa(rs), id).stream()
                .findAny().orElse(null);
    }

    public Mpa mapRowToMpa(ResultSet resultSet) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("MPA_ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .build();
    }
}
