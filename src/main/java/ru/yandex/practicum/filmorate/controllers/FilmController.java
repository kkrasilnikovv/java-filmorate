package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private static FilmService service;
    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return service.getAllFilms();
    }
    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable Integer filmId){
        return service.getFilmById(filmId);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return service.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return service.updateFilm(film);
    }
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id,@PathVariable Integer userId) {
        service.addLike(id,userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public void remoteLike(@PathVariable Integer id,@PathVariable Integer userId) {
        service.remoteLike(id,userId);
    }
    @GetMapping("/popular")
    public List<Film> getMostPopular(@RequestParam(defaultValue = "10") Integer count){
        return service.getMostPopular(count);
    }

}
