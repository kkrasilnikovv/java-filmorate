package ru.yandex.practicum.filmorate.storage.jdbc;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAllGenre();

    Optional<Genre> findGenreById(Integer id);

    void deleteAllGenreByFilmId(Long filmId);

    Optional<Genre> createGenre(Genre genre);

    Optional<Genre> updateGenre(Genre genre);

}
