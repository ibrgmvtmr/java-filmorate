package ru.yandex.practicum.javafilmorate.storage.dbimpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User create(User user) {
        final String sqlQuery = "INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES ( ?, ?, ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, generatedId);
        user.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        return user;
    }

    @Override
    public User update(User user) {
        final String sqlQuery = "UPDATE USERS\n" +
                "SET NAME=?, LOGIN=?, EMAIL=?, BIRTHDAY=?\n" +
                "WHERE USER_ID=?;";
        int rowsUpdated = jdbcTemplate.update(sqlQuery, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());

        if (rowsUpdated > 0) {
            return user;
        } else {
            throw new NotFoundException("Пользователь с таким Id не найден");
        }
    }

    @Override
    public List<User> getUsers() {
        final String sqlQuery = "SELECT * FROM USERS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaRowToUser(rs)).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(Integer userId) {
        final String sqlQuery = "SELECT * FROM USERS WHERE USER_ID = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> mpaRowToUser(rs), userId);
        } catch (Throwable throwable) {
            throw new NotFoundException("User с таким id не найден");
        }
    }

    @Override
    public Set<Integer> readUserFriends(Integer userId) {
        final String sqlQuery = "SELECT FRIEND_ID\n" +
                "FROM FRIENDSHIPS\n" +
                "WHERE USER_ID = ?";
        Set<Integer> ids = new HashSet<>();
        SqlRowSet friends = jdbcTemplate.queryForRowSet(sqlQuery, userId);
        while (friends.next()) {
            ids.add(friends.getInt("FRIEND_ID"));
        }
        return ids;
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        final String sqlQuery = "SELECT * FROM USERS U, FRIENDSHIPS F1, FRIENDSHIPS F2 " +
                "WHERE U.USER_ID = F1.FRIEND_ID AND U.USER_ID = F2.FRIEND_ID AND F1.USER_ID = ? AND F2.USER_ID = ?";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mpaRowToUser(rs), userId, friendId);
    }


    public User mpaRowToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("USER_ID"))
                .name(resultSet.getString("NAME"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .friends(readUserFriends(resultSet.getInt("USER_ID")))
                .build();
    }

}
