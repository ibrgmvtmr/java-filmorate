package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(Integer userId, Integer friendId) {
        userStorage.getUser(userId).getFriends().add(friendId);
        userStorage.getUser(friendId).getFriends().add(userId);
        return userStorage.getUser(userId);
    }

    public User deleteFriend(Integer userId, Integer friendId) {
        userStorage.getUser(userId).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(userId);
        return userStorage.getUser(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        List<User> commonFriends = new ArrayList<>();
        for(Integer id: userStorage.getUser(userId).getFriends()){
            if(userStorage.getUser(friendId).getFriends().contains(id)){
                commonFriends.add(userStorage.getUser(userId));
            }
        }
        return commonFriends;
    }

    public List<User> getUserFriends(Integer id) {
        return List.of((User)userStorage.getUsers().get(id).getFriends());
    }
}
