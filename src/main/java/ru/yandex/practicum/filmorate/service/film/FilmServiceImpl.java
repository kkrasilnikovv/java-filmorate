package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;


@Slf4j
@Service
public class FilmServiceImpl implements FilmService{

    private final FilmStorage storage;
    private final UserServiceImpl userService;
    private Integer id = 0;

    @Autowired
    public FilmServiceImpl(@Qualifier("FilmDaoImpl") FilmStorage storage, UserServiceImpl userService) {
        this.storage = storage;
        this.userService = userService;

    }
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
        return storage.addFilm(film);
    }
    @Override
    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        return storage.updateFilm(film);

    }
    @Override
    public void addLike(Integer filmId, Integer userId) {
        Film film = storage.findById(filmId).get();
        User user = userService.getUserById(userId);

        film.addLike(userId);
        storage.addLike(filmId, userId);
        log.info("Пользователь: {} поставил лайк фильму: {}", user, film);
    }
    @Override
    public void remoteLike(Integer filmId, Integer userId) {
        Film film = storage.findById(filmId).get();
        User user = userService.getUserById(userId);
        film.remoteLike(userId);
        storage.removeLike(filmId, userId);
        log.info("Пользователь: {} убрал лайк с фильма: {}", user, film);
    }
    @Override
    public List<Film> getMostPopular(Integer count) {
        log.debug("Отправлен список популярных фильмов с параметром {}", count);
        return storage.getMostPopular(count);
    }

    private void createFilmId(Film film) {
        id++;
        film.setId(id);
    }


}
