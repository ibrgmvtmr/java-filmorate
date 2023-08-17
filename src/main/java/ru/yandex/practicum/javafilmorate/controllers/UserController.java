package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.addFriend(userId, friendId);
        return new ResponseEntity<>("Пользователь успешно добавлен в друзья", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.removeFriend(userId, friendId);
        return new ResponseEntity<>("Пользователь успешно удалён из списка друзей", HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getUsersFriends(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getUserFriends(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public ResponseEntity<?> getCommonFriends(@PathVariable Integer userId, @PathVariable Integer otherId) {
        return new ResponseEntity<>(userService.getCommonFriends(userId, otherId), HttpStatus.OK);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFoundException(final IllegalStateException e) {
        return Map.of("Error", e.getMessage());
    }

}
