package ru.yandex.practicum.filmorate.storage.jdbc;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> getAllGenre();

    Optional<Genre> getGenreById(Integer id);

    void deleteAllGenreByFilmId(Integer filmId);

    Optional<Genre> createGenre(Genre genre);

    Optional<Genre> updateGenre(Genre genre);

}
