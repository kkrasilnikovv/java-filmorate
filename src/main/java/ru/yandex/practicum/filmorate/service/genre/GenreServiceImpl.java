package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.MpaDaoImpl;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreDaoImpl genreDao;

    @Autowired
    public GenreServiceImpl(GenreDaoImpl genreDao) {
        this.genreDao = genreDao;
    }
    @Override
    public List<Genre> findAllGenre() {
        return genreDao.findAllGenre();
    }

    @Override
    public Optional<Genre> findGenreById(Integer id) {
        return genreDao.findGenreById(id);
    }

    @Override
    public void deleteAllGenreByFilmId(Long filmId) {
        genreDao.deleteAllGenreByFilmId(filmId);
    }

    @Override
    public Optional<Genre> createGenre(Genre genre) {
        return genreDao.createGenre(genre);
    }

    @Override
    public Optional<Genre> updateGenre(Genre genre) {
        return genreDao.updateGenre(genre);
    }

}
