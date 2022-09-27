package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.jdbc.FilmGenreDao;
import ru.yandex.practicum.filmorate.storage.jdbc.FilmStorage;
import ru.yandex.practicum.filmorate.storage.jdbc.LikeDao;
import ru.yandex.practicum.filmorate.storage.jdbc.UserStorage;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage storage;
    private final LikeDao likeStorage;
    private final UserStorage userStorage;
    private final FilmGenreDao filmGenreStorage;
    private Integer id = 0;


    @Override
    public List<Film> getAllFilms() {
        log.debug("Получен запрос GET /films.");
        return storage.getAllFilms();
    }

    @Override
    public Film getFilmById(Integer filmId) {
        return storage.findById(filmId).orElseThrow(() ->
                new NotFoundException("Фильм с id " + filmId + " не найден."));
    }

    @Override
    public Film addFilm(Film film) {
        createFilmId(film);
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
        userStorage.getUserById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + filmId + " не найден."));
        likeStorage.addLike(filmId, userId);
        log.info("Пользователь: {} поставил лайк фильму: {}", userId, filmId);
    }

    @Override
    public void remoteLike(Integer filmId, Integer userId) {
        getFilmById(filmId);
        userStorage.getUserById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + filmId + " не найден."));
        likeStorage.removeLike(filmId, userId);
        log.info("Пользователь: {} убрал лайк с фильма: {}", userId, filmId);
    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        log.debug("Отправлен список популярных фильмов с параметром {}", count);
        return likeStorage.getMostPopular(count);
    }

    private void createFilmId(Film film) {
        id++;
        film.setId(id);
    }


}
