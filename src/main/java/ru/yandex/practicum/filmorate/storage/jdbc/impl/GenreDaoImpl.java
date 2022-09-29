package ru.yandex.practicum.filmorate.storage.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.jdbc.GenreDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenre() {
        final String sql = "SELECT * FROM GENRES";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        return mappingGenre(rs);
    }

    @Override
    public Optional<Genre> getGenreById(Integer id) {
        final String sql = "SELECT * FROM GENRES WHERE genre_id =  ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        List<Genre> genres = mappingGenre(rs);
        if (genres.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(genres.get(0));
        }
    }

    @Override
    public void deleteAllGenreByFilmId(Integer filmId) {
        final String sql = "DELETE FROM FILMS_GENRES where GENRE_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }

    @Override
    public Optional<Genre> createGenre(Genre genre) {
        final String sql = "INSERT INTO GENRES (NAME) VALUES (?)";
        if (jdbcTemplate.update(sql, genre.getName()) != 1) {
            return Optional.empty();
        } else {
            return Optional.of(genre);
        }
    }

    @Override
    public Optional<Genre> updateGenre(Genre genre) {
        final String sql = "UPDATE GENRES SET NAME = ? WHERE genre_id = ?";
        if (jdbcTemplate.update(sql, (genre.getId())) != 1) {
            return Optional.empty();
        } else {
            return Optional.of(genre);
        }
    }

    private List<Genre> mappingGenre(SqlRowSet rs) {
        List<Genre> genres = new ArrayList<>();
        while (rs.next()) {
            Genre genre = new Genre(rs.getInt("GENRE_ID"), rs.getString("NAME"));
            genres.add(genre);
        }
        return genres;
    }
}
