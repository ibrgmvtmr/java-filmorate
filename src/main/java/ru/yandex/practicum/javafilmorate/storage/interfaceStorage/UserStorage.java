package ru.yandex.practicum.javafilmorate.storage.interfaceStorage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> getUsers();

    void createUser(User user);

    void updateUser(User user);

    Optional<User> getUser(int userId);

    List<User> getUsersFriends(Integer id);

    List<User> getCommonFriends(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);

    void addFriend(Integer userId, Integer friendId);

}
