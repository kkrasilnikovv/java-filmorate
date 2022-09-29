package ru.yandex.practicum.filmorate.storage.jdbc;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeDao {
    List<Film> getMostPopular(Integer count);
    void addLike(Integer filmId, Integer userId);
    void removeLike(Integer filmId, Integer userId);
    List<Integer> getLikesByFilm(Integer id);
}
