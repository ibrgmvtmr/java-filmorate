package ru.yandex.practicum.javafilmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.interfaceStorage.UserStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long generatedId = 1;

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
    public Optional<User> getUser(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (getUser(userId).isPresent()) {
            if (getUser(friendId).isPresent()) {
                getUser(userId).get().getFriends().add(friendId);
                getUser(friendId).get().getFriends().add(userId);
            }
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        users.get(userId).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(userId);
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long friendId) {
        List<User> commonFriends = new ArrayList<>();
        if (getUser(userId).isPresent()) {
            for (Long id : getUser(userId).get().getFriends()) {
                if (users.get(friendId).getFriends().contains(id)) {
                    commonFriends.add(users.get(id));
                }
            }
        }
        return commonFriends;
    }

    @Override
    public List<User> getUsersFriends(Long userId) {
        List<User> usersFriends = new ArrayList<>();
        for (Long friendId : users.get(userId).getFriends()) {
            usersFriends.add(users.get(friendId));
        }
        return usersFriends;
    }
}
