package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    void reset();

    List<Film> getAllFilms();

    Film findById(Integer id);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getMostPopular(Integer count);
}
