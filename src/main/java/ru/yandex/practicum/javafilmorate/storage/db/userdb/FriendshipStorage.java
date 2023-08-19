package ru.yandex.practicum.javafilmorate.storage.db.userdb;


public interface FriendshipStorage {

    void addFriend(Integer userId, Integer friendId);

    void removeFriend(Integer userId, Integer friendId);
}
