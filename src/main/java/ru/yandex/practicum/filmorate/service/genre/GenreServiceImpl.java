package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.jdbc.GenreDao;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
    private final GenreDao genreDao;

    @Override
    public List<Genre> findAllGenre() {
        return genreDao.getAllGenre();
    }

    @Override
    public Optional<Genre> findGenreById(Integer id) {
        return genreDao.getGenreById(id);
    }

    @Override
    public void deleteAllGenreByFilmId(Integer filmId) {
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
