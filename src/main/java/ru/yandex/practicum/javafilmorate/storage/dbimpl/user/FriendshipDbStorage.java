package ru.yandex.practicum.javafilmorate.storage.dbimpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.FriendshipStorage;

import java.util.List;

@Component
public class FriendshipDbStorage implements FriendshipStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sqlQuery = "INSERT INTO FRIENDSHIPS\n" +
                          "(USER_ID, FRIEND_ID)\n" +
                          "VALUES(?,?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> readUserFriends(Integer userId) {
        String sqlQuery = "SELECT *\n" +
                "FROM USERS\n" +
                "WHERE USER_ID IN (\n" +
                "    SELECT FRIEND_ID\n" +
                "    FROM FRIENDSHIPS\n" +
                "    WHERE USERS.USER_ID = ?\n" +
                ");";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        String sqlQuery = "SELECT u.*\n" +
                "FROM USERS u\n" +
                "JOIN FRIENDSHIPS f1 ON u.user_id = f1.friend_id\n" +
                "JOIN FRIENDSHIPS f2 ON f1.user_id = f2.friend_id\n" +
                "WHERE f1.user_id = ? AND f2.user_id = ?;";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId, friendId);
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        String sql = "DELETE FROM FRIENDSHIPS\n" +
                "WHERE FRIEND_ID=? AND USER_ID=?;\n";
        jdbcTemplate.update(sql, friendId, userId);
    }
}
