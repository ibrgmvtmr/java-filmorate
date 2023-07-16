package ru.yandex.practicum.javafilmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getFilm(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUser(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable String userId, @PathVariable String friendId) {
        userService.addFriend(Integer.parseInt(userId), Integer.parseInt(friendId));
        return new ResponseEntity<>("Пользователь успешно добавлен в друзья", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable String userId, @PathVariable String friendId) {
        userService.deleteFriend(Integer.parseInt(userId), Integer.parseInt(friendId));
        return new ResponseEntity<>("Пользователь успешно удалён из списка друзей", HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getUsersFriends(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUsersFriends(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public ResponseEntity<?> getCommonFriends(@PathVariable String userId, @PathVariable String otherId) {
        return new ResponseEntity<>(userService.getCommonFriends(Integer.parseInt(userId), Integer.parseInt(otherId)), HttpStatus.OK);
    }

}
