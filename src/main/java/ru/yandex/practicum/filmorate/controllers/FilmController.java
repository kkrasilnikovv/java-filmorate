package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    @GetMapping
    public List<Film> getAllFilms() {
        return service.getAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable Integer filmId) {
        return service.getFilmById(filmId);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        isValid(film);
        return service.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        isValid(film);
        return service.updateFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        service.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void remoteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        service.remoteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopular(@Positive @RequestParam(defaultValue = "10") Integer count) {
        return service.getMostPopular(count);
    }

    private void isValid(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Передан запрос POST с некорректным данными фильима {}.", film);
            throw new ValidationException("Некорректные данные фильма.");
        }
    }

}
