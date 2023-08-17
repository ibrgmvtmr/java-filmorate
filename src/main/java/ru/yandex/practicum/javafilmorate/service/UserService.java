package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.db.userdb.FriendshipStorage;
import ru.yandex.practicum.javafilmorate.storage.dbimpl.user.UserDbStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final UserDbStorage userDbStorage;
    private final FriendshipStorage friendshipStorage;

    public UserService(UserDbStorage userDbStorage, FriendshipStorage friendshipStorage) {
        this.userDbStorage = userDbStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Пользователь {} создан", user);
        return userDbStorage.create(user);
    }

    public User update(User user) {
        log.debug("Началось обновление пользователя с id = {}", user.getId());
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userDbStorage.update(user);
    }

    public User getUser(Integer userId) {
        User user = userDbStorage.getUser(userId);
        if (user == null) {
            log.debug("Пользователь с id = {} не существует", userId);
            throw new NotFoundException("Пользователь с указанным id не найден!");
        }
        return user;
    }

    public Collection<User> getUsers() {
        return userDbStorage.getUsers();
    }

    public void addFriend(Integer userId, Integer friendId) {
        friendshipStorage.addFriend(userId, friendId);
        log.debug("Друг добавлен");
    }

    public void removeFriend(Integer userId, Integer friendId) {
        friendshipStorage.removeFriend(userId, friendId);
        log.debug("Друг удалён");
    }

    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        log.debug("Получен запрос на получение списка общих друзей");
        return friendshipStorage.getCommonFriends(userId, friendId);
    }

    public Set<User> getUserFriends(Integer userId) {
        log.debug("Запрос на получение друзей для пользователя с ID: {}", userId);
        Set<User> friends = new HashSet<>();
        for(Integer id: userDbStorage.readUserFriends(userId)) {
            friends.add(userDbStorage.getUser(id));
        }
        return friends;
    }
}
