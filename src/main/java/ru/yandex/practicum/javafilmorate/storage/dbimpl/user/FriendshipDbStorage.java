package ru.yandex.practicum.javafilmorate.storage.dbimpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.FriendshipStorage;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.UserStorage;


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
    public void removeFriend(Integer userId, Integer friendId) {
        final String sqlQuery = "DELETE FROM FRIENDSHIPS\n" +
                "WHERE FRIEND_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sqlQuery, friendId, userId);
    }
}
