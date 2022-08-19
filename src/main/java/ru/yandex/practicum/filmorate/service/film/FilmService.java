package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Service
public class FilmService {

    private final FilmStorage storage;
    private final UserService userService;
    private final static LocalDate BIRTH_MOVIE = LocalDate.of(1895, 12, 28);
    private Integer id = 0;

    @Autowired
    public FilmService(FilmStorage storage, UserService userService) {
        this.storage = storage;
        this.userService = userService;

    }

    public List<Film> getAllFilms() {
        log.debug("Получен запрос GET /films.");
        return storage.getAllFilms();
    }

    public Film getFilmById(Integer filmId) {
        return storage.findById(filmId).orElseThrow(() -> new NotFoundException("Фильм с id " + filmId + " не найден."));
    }

    public Film addFilm(Film film) {
        isValid(film);
        createFilmId(film);
        log.debug("Получен запрос POST. Передан обьект {}", film);
        return storage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        isValid(film);
        if (storage.findById(film.getId()).isPresent()) {
            return storage.updateFilm(film);
        } else {
            log.error("Передан запрос PUT с некорректным данными фильима {}.", film);
            throw new NotFoundException("Такого фильма не существует.");
        }
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        User user = userService.findById(userId);
        film.addLike(userId);
        log.info("Пользователь: {} поставил лайк фильму: {}", user, film);
    }

    public void remoteLike(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        User user = userService.findById(userId);
        film.remoteLike(userId);
        log.info("Пользователь: {} убрал лайк с фильма: {}", user, film);
    }

    public List<Film> getMostPopular(Integer count) {
        log.debug("Отправлен список популярных фильмов с параметром {}", count);
        return storage.getMostPopular(count);
    }

    private void createFilmId(Film film) {
        id++;
        film.setId(id);
    }

    private void isValid(Film film) {
        if (film.getReleaseDate().isBefore(BIRTH_MOVIE)) {
            log.error("Передан запрос POST с некорректным данными фильима {}.", film);
            throw new ValidationException("Некорректные данные фильма.");
        }
    }


}
