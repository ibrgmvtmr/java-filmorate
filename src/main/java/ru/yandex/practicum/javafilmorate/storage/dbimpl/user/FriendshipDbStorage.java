package ru.yandex.practicum.javafilmorate.storage.dbimpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.FriendshipStorage;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendshipDbStorage implements FriendshipStorage {

    private final JdbcTemplate jdbcTemplate;

    private final UserStorage userStorage;

    @Autowired
    public FriendshipDbStorage(JdbcTemplate jdbcTemplate, UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sqlQuery = "INSERT INTO FRIENDSHIPS\n" +
                "(USER_ID, FRIEND_ID)\n" +
                "VALUES(?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, userStorage.getUser(userId).getId(), userStorage.getUser(friendId).getId());
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        String sql = "SELECT * \n" +
                "FROM USERS u \n" +
                "WHERE u.USER_ID \n" +
                "IN (SELECT FRIEND_ID FROM FRIENDSHIPS f  WHERE user_id = ?)\n" +
                "AND USER_ID IN (SELECT FRIEND_ID FROM FRIENDSHIPS WHERE user_id = ?);";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mpaRowToUser(rs), userStorage.getUser(userId).getId(), userStorage.getUser(userId).getId());
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        String sql = "DELETE FROM FRIENDSHIPS\n" +
                "WHERE FRIEND_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, friendId, userId);
    }

    public User mpaRowToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .name(resultSet.getString("NAME"))
                .login(resultSet.getString("LOGIN"))
                .email(resultSet.getString("EMAIL"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .friends(userStorage.readUserFriends(resultSet.getInt("USER_ID")))
                .build();
    }
}
