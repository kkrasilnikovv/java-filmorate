package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;


import java.util.List;
import java.util.Optional;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreService service;


    @GetMapping("{id}")
    public Optional<Genre> getGenre(@PathVariable Integer id) {
        if (id < 1) {
            throw new NotFoundException("Жанр с id " + id + " не найден.");
        }
        return service.findGenreById(id);
    }


    @GetMapping
    public List<Genre> findAll() {
        log.info("findAll");
        return service.findAllGenre();
    }
}
