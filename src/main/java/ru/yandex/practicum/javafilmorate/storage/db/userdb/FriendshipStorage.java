package ru.yandex.practicum.javafilmorate.storage.db.userdb;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface FriendshipStorage {
    void addFriend(Integer userId, Integer friendId);

    List<User> readUserFriends(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer friendId);

    void removeFriend(Integer userId, Integer friendId);
}
