package ru.yandex.practicum.javafilmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.UserStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Поступил запрос на создание пользователя.");
        return userStorage.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Поступил запрос на обновление пользователя.");
        return userStorage.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Поступил запрос на добавления в друзья.");
        return userService.addFriend(Integer.parseInt(id), Integer.parseInt(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Поступил запрос на удаление из друзей.");
        userService.deleteFriend(Integer.parseInt(id), Integer.parseInt(friendId));
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable String id) {
        log.info("Поступил запрос на получение списка друзей.");
        return userService.getUserFriends(Integer.parseInt(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        log.info("Поступил запрос на получения списка общих друзей.");
        return userService.getCommonFriends(Integer.parseInt(id), Integer.parseInt(otherId));
    }

}
