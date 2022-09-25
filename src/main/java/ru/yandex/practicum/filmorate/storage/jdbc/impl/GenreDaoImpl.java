package ru.yandex.practicum.filmorate.storage.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.jdbc.GenreDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> findAllGenre() {
        final String sql="SELECT * FROM GENRES";
        return jdbcTemplate.queryForStream(sql,
                (rs, rowNum) -> new Genre(rs.getInt("GENRE_ID"), rs.getString("name"))).
                collect(Collectors.toList());
    }

    @Override
    public Optional<Genre> findGenreById(Integer id) {
        final String sql="SELECT * FROM GENRES WHERE genre_id =  ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        if (rs.next()) {
            return Optional.of(new Genre(rs.getInt(1),
                    rs.getString(2)));
        }
        return Optional.empty();
    }

    @Override
    public void deleteAllGenreByFilmId(Long filmId) {
        final String sql = "DELETE FROM FILMS_GENRES where GENRE_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }

    @Override
    public Optional<Genre> createGenre(Genre genre) {
        final String sql="INSERT INTO GENRES (NAME) VALUES (?)";
        if (jdbcTemplate.update(sql, genre.getName()) != 1) {
            return Optional.empty();
        } else {
            return Optional.of(genre);
        }
    }

    @Override
    public Optional<Genre> updateGenre(Genre genre) {
        final String sql="UPDATE GENRES SET NAME = ? WHERE genre_id = ?";
        if (jdbcTemplate.update(sql, (genre.getId())) != 1) {
            return Optional.empty();
        } else {
            return Optional.of(genre);
        }
    }
}
