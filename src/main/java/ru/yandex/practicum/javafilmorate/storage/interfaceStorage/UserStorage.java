package ru.yandex.practicum.javafilmorate.storage.interfaceStorage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getUsers();

    User createUser(User user);

    User updateUser(User user);

    User getUser(Integer id);

}
