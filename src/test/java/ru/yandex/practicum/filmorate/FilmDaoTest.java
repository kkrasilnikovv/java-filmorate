package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.FilmDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDaoTest {
    private final FilmDaoImpl storage;

    @Test
    public void addFilmAndFindByIdTest() {
        storage.addFilm(new Film(1, "Фильм", "Крутой фильм",
                LocalDate.of(2003, 3, 31), 50, new Mpa(1, "Боевик")));
        if (storage.findById(1).isPresent()) {
            assertEquals(storage.findById(1).get().getId(),1);
        }
    }
    @Test
    public void getAllFilmsTest(){
        storage.addFilm(new Film(2, "Фильм2", "Крутой фильм2",
                LocalDate.of(2003, 3, 31), 50, new Mpa(1, "Боевик")));
        assertEquals(storage.getAllFilms().size(),2);
    }
    @Test
    public void updateFilmTest(){
        storage.updateFilm(new Film(1, "Мультик", "Мульт",
                LocalDate.of(2003, 3, 31), 50, new Mpa(1, "Боевик")));
        if (storage.findById(1).isPresent()) {
            assertEquals(storage.findById(1).get().getName(),"Мультик");
        }
    }
}
