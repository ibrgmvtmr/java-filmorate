package ru.yandex.practicum.javafilmorate.storage.dbimpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.LikesStorage;

import java.util.List;

@Component
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        String sqlQuery = "INSERT INTO LIKES(FILM_ID, USER_ID)\n" +
                "VALUES(?, ?);";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Integer> readUsersLikes(Integer filmId) {
        String sql = "SELECT USER_ID\n" +
                "FROM LIKES\n" +
                "WHERE FILM_ID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, filmId);
    }

    @Override
    public void deleteLike(Integer userId, Integer filmId) {
        String sqlQuery = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }
}
