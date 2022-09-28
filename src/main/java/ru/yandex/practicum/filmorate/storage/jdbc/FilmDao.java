package ru.yandex.practicum.filmorate.storage.jdbc;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDao {

    void reset();

    List<Film> getAllFilms();

    Optional<Film> findById(Integer id);

    Film addFilm(Film film);

    int updateFilm(Film film);


}
