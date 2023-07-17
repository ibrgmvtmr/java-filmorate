package ru.yandex.practicum.javafilmorate.storage.interfacestorages;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getUsers();

    void createUser(User user);

    void updateUser(User user);

    User getUser(Integer userId);

    List<User> getUsersFriends(Integer id);

    List<User> getCommonFriends(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);

    void addFriend(Integer userId, Integer friendId);

}
