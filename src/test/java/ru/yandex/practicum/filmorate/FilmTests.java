package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;

public class FilmTests {
    @Test
    void LoginWithSpace(){
        FilmController filmController= new FilmController();
        Film film=new Film(" ","Происходит только хорошее",
                LocalDate.of(1795,12,28),0);
        Throwable exception = Assertions.assertThrows(
                ValidationException.class,
                () -> {
                    filmController.createFilm(film);
                }
        );
        Assertions.assertEquals("400 BAD_REQUEST", exception.getMessage());
    }
}
