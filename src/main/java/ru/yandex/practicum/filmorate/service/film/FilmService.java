package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;


@Slf4j
@Service
public class FilmService {

    private final FilmStorage storage;
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage storage, UserService userService) {
        this.storage = storage;
        this.userService = userService;

    }

    public List<Film> getAllFilms() {
        return storage.getAllFilms();
    }

    public Film getFilmById(Integer filmId) {
        return storage.findById(filmId);
    }
    public Film addFilm(Film film){
        return storage.addFilm(film);
    }
    public Film updateFilm(Film film) {
        return storage.updateFilm(film);
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = storage.findById(filmId);
        User user = userService.findById(userId);

        film.addLike(userId);
        log.info("Пользователь: {} поставил лайк фильму: {}", user, film);
    }

    public void remoteLike(Integer filmId, Integer userId) {
        Film film = storage.findById(filmId);
        User user = userService.findById(userId);
        film.remoteLike(userId);
        log.info("Пользователь: {} убрал лайк с фильма: {}",user, film);
    }
    public List<Film> getMostPopular(Integer count) {
        return storage.getMostPopular(count);
    }


}
