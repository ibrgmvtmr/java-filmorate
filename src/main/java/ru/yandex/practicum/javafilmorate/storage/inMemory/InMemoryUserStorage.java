package ru.yandex.practicum.javafilmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int generatedId = 1;

    @Override
    public User createUser(User user) {
        log.info("Добавление нового пользователя: {}", user);
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(generatedId++);
        log.info("Пользователь успешно добавлен: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
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

    @Override
    public List<User> getUsers() {
        log.info("Получение всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(Integer id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }
}
