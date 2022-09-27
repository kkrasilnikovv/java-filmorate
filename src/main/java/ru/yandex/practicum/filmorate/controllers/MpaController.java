package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaServiceImpl;


    @GetMapping("{id}")
    public Optional<Mpa> getMpaById(@PathVariable Integer id) {
        if (id < 1) {
            throw new NotFoundException("Рейтинг с id " + id + " не найден.");
        }
        return mpaServiceImpl.getMpaById(id);
    }

    @GetMapping()
    public List<Mpa> getAllMpa() {
        return mpaServiceImpl.getAllMpa();
    }
}
