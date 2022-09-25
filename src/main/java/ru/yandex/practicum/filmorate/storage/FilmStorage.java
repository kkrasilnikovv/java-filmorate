package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    void reset();

    List<Film> getAllFilms();

    Optional<Film> findById(Integer id);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getMostPopular(Integer count);
    void addLike(Integer filmId, Integer userId);
    void removeLike(Integer filmId, Integer userId);
}
