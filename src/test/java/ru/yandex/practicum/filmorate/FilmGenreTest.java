/*package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import static org.junit.jupiter.api.Assertions.*;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.FilmDaoImpl;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.FilmGenreDaoImpl;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmGenreTest {
    private final FilmGenreDaoImpl storage;
    private final FilmDaoImpl filmStorage;

    @Test
    public void addNewGenreToFilmAndFindAllByFilmIdTest(){
        filmStorage.addFilm(new Film(1,"Фильм", "Крутой фильм",
                LocalDate.of(2003, 3, 31), 50, new Mpa(1, "Боевик")));
        Genre test= new Genre(1,"Боевик");
        Genre test1= new Genre(2,"Ужасы");
        storage.addNewGenreToFilm(1,test);
        storage.addNewGenreToFilm(1,test1);
        assertEquals(storage.findAllByFilmId(1).size(),2);
    }
    @Test
    public void updateAllGenreByFilmTest(){
        Film test = new Film(1,"Фильм-Вторая часть", "Уже не такой крутой фильм",
                LocalDate.of(2003, 3, 31), 50, new Mpa(1, "Боевик"));
        filmStorage.updateFilm(test);
        storage.updateAllGenreByFilm(test);
        assertEquals(storage.findAllByFilmId(1).size(),2);
    }
}*/
