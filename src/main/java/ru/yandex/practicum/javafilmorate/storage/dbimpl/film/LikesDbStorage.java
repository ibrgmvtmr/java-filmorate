package ru.yandex.practicum.javafilmorate.storage.dbimpl.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.db.filmdb.LikesStorage;

import java.util.HashSet;
import java.util.Set;

@Component
public class LikesDbStorage implements LikesStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public LikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Integer userId, Integer filmId) {
        String sqlQuery = "INSERT INTO LIKES(USER_ID, FILM_ID)\n" +
                "VALUES(?,?);";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    @Override
    public Set<Integer> readUsersLikes(Integer filmId) {
        String sql = "SELECT USER_ID\n" +
                "FROM LIKES\n" +
                "WHERE FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.queryForList(sql, Integer.class, filmId));
    }

    @Override
    public void deleteLike(long userId, long filmId) {
        String sqlQuery = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }
}
