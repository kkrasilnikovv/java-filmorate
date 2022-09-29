package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;


public interface FilmService {
    List<Film> getAllFilms();
    Film addFilm(Film film);

    Film getFilmById(Integer filmId);

    Film updateFilm(Film film);

    void addLike(Integer filmId, Integer userId);

    void remoteLike(Integer filmId, Integer userId);

    List<Film> getMostPopular(Integer count);
}
