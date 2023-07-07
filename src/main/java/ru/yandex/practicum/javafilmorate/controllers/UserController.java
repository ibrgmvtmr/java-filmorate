package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, User> users = new HashMap<>();
    private int generatedId = 1;

    @PostMapping
    public User createUser(@Valid @RequestBody User user){
        log.info("Добавление нового пользователя: {}", user);
        if (user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(generatedId++);
        log.info("Пользователь успешно добавлен: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @Valid @RequestBody User updatedUser){
        if(users.containsKey(id)){
            log.info("Обновление аккаунта");
            updatedUser.setId(id);
            users.put(updatedUser.getId(), updatedUser);
            return updatedUser;
        } else {
            log.warn("Валидация пользователя не прошла");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @GetMapping
    public List<User> getUsers(){
        log.info("Получение всех пользователей");
        return new ArrayList<>(users.values());
    }
}
