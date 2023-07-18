package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.inmemory.InMemoryUserStorage;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        inMemoryUserStorage.createUser(user);
        log.debug("Пользователь добавлен");
    }

    public void updateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (inMemoryUserStorage.getUser(user.getId()) == null) {
            String msg = "Пользователь с таким ID не найден";
            log.warn(msg);
            throw new NotFoundException(msg);
        } else {
            inMemoryUserStorage.updateUser(user);
            log.debug("Пользователь обновлён");
        }
    }

    public List<User> getUsers() {
        log.debug("Получение всех пользователей");
        return inMemoryUserStorage.getUsers();
    }

    public User getUser(Integer userId) {
        if (inMemoryUserStorage.getUser(userId) == null) {
            String msg = "Пользователя с таким ID не существует";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        log.debug("Получение пользователя по ID");
        return inMemoryUserStorage.getUser(userId);
    }

    public void addFriend(Integer userId, Integer friendId) {
        userIdChecker(userId, friendId);
        inMemoryUserStorage.addFriend(userId, friendId);
        log.debug("Друг добавлен");
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        userIdChecker(userId, friendId);
        inMemoryUserStorage.deleteFriend(userId, friendId);
        log.debug("Friend Deleted");
    }

    public List<User> getUsersFriends(Integer userId) {
        if (inMemoryUserStorage.getUser(userId) == null) {
            String msg = "Пользователь с таким ID не найден";
            log.warn(msg);
            throw new NotFoundException(msg);
        } else {
            return inMemoryUserStorage.getUsersFriends(userId);
        }
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        userIdChecker(userId, friendId);
        log.debug("получение общих знакомых");
        return inMemoryUserStorage.getCommonFriends(userId, friendId);
    }

    public void userIdChecker(Integer userId, Integer friendId) {
        if (inMemoryUserStorage.getUser(userId) == null) {
            String msg = "Пользователь с таким ID не найден";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (inMemoryUserStorage.getUser(friendId) == null) {
            String msg = "Пользователь с таким ID не найден";
            log.warn(msg);
            throw new NotFoundException(msg);
        }
    }
}
