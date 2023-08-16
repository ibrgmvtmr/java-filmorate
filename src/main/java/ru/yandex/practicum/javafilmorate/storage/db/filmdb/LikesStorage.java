package ru.yandex.practicum.javafilmorate.storage.db.filmdb;

import java.util.Set;

public interface LikesStorage {
    void addLike(Integer userId, Integer filmId);
    Set<Integer> readUsersLikes(Integer filmId);
    void deleteLike(long userId, long filmId);

}
