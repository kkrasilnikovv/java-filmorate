package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.FilmDaoImpl;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.LikeDaoImpl;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.UserDaoImpl;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDaoTest {
    private final LikeDaoImpl storage;
    private final FilmDaoImpl filmStorage;
    private final UserDaoImpl userStorage;

    @BeforeEach
    public void updateDatabase() {
        userStorage.reset();
        userStorage.addUser(new User("Ivan", "mail@mail.ru", "CoolMan",
                LocalDate.of(2003, 3, 31)));
        userStorage.addUser(new User("Alex", "yandex@yandex.ru", "BadMan",
                LocalDate.of(2012, 5, 10)));
        userStorage.addUser(new User("Sasha", "google@yandex.ru", "smallMan",
                LocalDate.of(1999, 8, 17)));
        filmStorage.reset();
        filmStorage.addFilm(new Film(1,"Фильм", "Крутой фильм",
                LocalDate.of(2003, 3, 31), 50, new Mpa(1, "Боевик")));
        filmStorage.addFilm(new Film(2,"Фильм-Второй", "Не очень крутой фильм",
                LocalDate.of(2009, 4, 12), 100, new Mpa(2, "Драма")));
    }

    @Test
    public void addLikeAndGetLikesByFilmTest() {
        storage.addLike(1, 1);
        storage.addLike(1, 2);
        storage.addLike(1, 3);
        storage.addLike(2, 1);
        assertEquals(storage.getLikesByFilm(1).size(), 3);
        assertEquals(storage.getLikesByFilm(2).size(), 1);
    }

    @Test
    public void getMostPopularTest() {
        assertEquals(storage.getMostPopular(2).get(0).getId(),1);
        assertEquals(storage.getMostPopular(1).get(0).getId(),1);
    }
    @Test
    public void removeLikeTest(){
        storage.removeLike(1,1);
        assertEquals(storage.getLikesByFilm(1).size(), 2);
        storage.removeLike(1,2);
        assertEquals(storage.getLikesByFilm(1).size(), 1);
    }
}