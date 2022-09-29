package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.GenreDaoImpl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDaoTest {
    private final GenreDaoImpl storage;
    @Test
    public void getAllGenreTest(){
        List<Genre> genres = storage.getAllGenre();
        assertEquals(genres.size(),6);
    }
    @Test
    public void getGenreByIdTest(){
        if(storage.getGenreById(1).isPresent()){
            assertEquals(storage.getGenreById(1).get().getName(),"Комедия");
        }
    }
    @Test
    public void createGenreTest() {
        Genre test = new Genre(7, "Модерн");
        if (storage.createGenre(test).isPresent()) {
            storage.createGenre(test);
        }
        if (storage.getGenreById(7).isPresent()) {
            assertEquals(storage.getGenreById(7).get(),test);
        }
    }
    @Test
    public void updateGenreTest() {
        Genre test = new Genre(7, "Супер-Модерн");
        if (storage.createGenre(test).isPresent()) {
            storage.updateGenre(test);
        }
        if (storage.getGenreById(7).isPresent()) {
            assertEquals(storage.getGenreById(7).get().getName(), "Супер-Модерн");
        }
    }
}
