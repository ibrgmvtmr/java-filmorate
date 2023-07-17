package ru.yandex.practicum.javafilmorate.storage.interfaceStorage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> getUsers();

    void createUser(User user);

    void updateUser(User user);

    Optional<User> getUser(Long userId);

    List<User> getUsersFriends(Long id);

    List<User> getCommonFriends(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    void addFriend(Long userId, Long friendId);

}
