package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.jdbc.FilmGenreDao;
import ru.yandex.practicum.filmorate.storage.jdbc.FilmDao;
import ru.yandex.practicum.filmorate.storage.jdbc.LikeDao;
import ru.yandex.practicum.filmorate.storage.jdbc.UserDao;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmDao storage;
    private final LikeDao likeStorage;
    private final UserDao userDao;
    private final FilmGenreDao filmGenreStorage;

    @Override
    public List<Film> getAllFilms() {
        log.debug("Получен запрос GET /films.");
        List<Film> films = new ArrayList<>();
        for(Film film:storage.getAllFilms()){
            film.setGenres(new ArrayList<>(filmGenreStorage.findAllByFilmId(film.getId())));
            films.add(film);
        }
        return films;
    }

    @Override
    public Film getFilmById(Integer filmId) {
         Film film=storage.findById(filmId).orElseThrow(() ->
                new NotFoundException("Фильм с id " + filmId + " не найден."));
         film.setGenres(new ArrayList<>(filmGenreStorage.findAllByFilmId(filmId)));
         return film;
    }

    @Override
    public Film addFilm(Film film) {
        log.debug("Получен запрос POST. Передан обьект {}", film);
        Film temp = storage.addFilm(film);
        filmGenreStorage.updateAllGenreByFilm(temp);
        return temp;
    }

    @Override
    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        if (storage.updateFilm(film) == 1) {
            filmGenreStorage.updateAllGenreByFilm(film);
        }
        return film;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        getFilmById(filmId);
        userDao.getUserById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + filmId + " не найден."));
        likeStorage.addLike(filmId, userId);
        log.info("Пользователь: {} поставил лайк фильму: {}", userId, filmId);
    }

    @Override
    public void remoteLike(Integer filmId, Integer userId) {
        getFilmById(filmId);
        userDao.getUserById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + filmId + " не найден."));
        likeStorage.removeLike(filmId, userId);
        log.info("Пользователь: {} убрал лайк с фильма: {}", userId, filmId);
    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        log.debug("Отправлен список популярных фильмов с параметром {}", count);
        return likeStorage.getMostPopular(count);
    }
}
