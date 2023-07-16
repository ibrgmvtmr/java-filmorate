package ru.yandex.practicum.javafilmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.UserStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int generatedId = 1;

    @Override
    public void createUser(User user) {
        user.setId(generatedId++);
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getUser(int userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        if(getUser(userId).isPresent()){
            if(getUser(friendId).isPresent()){
                getUser(userId).get().getFriends().add(friendId);
                getUser(friendId).get().getFriends().add(userId);
            }
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        users.get(userId).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(userId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        List<User> commonFriends = new ArrayList<>();
        for (Integer id : users.get(userId).getFriends()) {
            if (users.get(friendId).getFriends().contains(id)) {
                commonFriends.add(users.get(userId));
            }
        }
        return commonFriends;
    }

    @Override
    public List<User> getUsersFriends(Integer userId) {
        List<User> usersFriends = new ArrayList<>();
        for (Integer friendId : users.get(userId).getFriends()) {
            usersFriends.add(users.get(friendId));
        }
        return usersFriends;
    }

    public boolean isUsersFriend(int userId, int friendId) {
        boolean isUsersFriend;
        isUsersFriend = users.get(userId).getFriends().contains(friendId);
        return isUsersFriend;
    }
}
