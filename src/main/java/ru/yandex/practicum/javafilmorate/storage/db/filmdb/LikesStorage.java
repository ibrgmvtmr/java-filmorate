package ru.yandex.practicum.javafilmorate.storage.db.filmdb;

import java.util.List;

public interface LikesStorage {
    void addLike(Integer userId, Integer filmId);

    List<Integer> readUsersLikes(Integer filmId);

    void deleteLike(long userId, long filmId);

}
