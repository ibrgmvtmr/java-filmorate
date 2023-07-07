package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int generatedId = 1;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Добавление нового пользователя: {}", user);
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(generatedId++);
        log.info("Пользователь успешно добавлен: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            log.info("Обновление аккаунта");
            users.put(user.getId(), user);
            return user;
        } else {
            log.warn("Валидация пользователя не прошла");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получение всех пользователей");
        return new ArrayList<>(users.values());
    }
}