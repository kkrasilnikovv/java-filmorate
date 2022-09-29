package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.MpaDaoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDaoTest {
    private final MpaDaoImpl storage;
    @Test
    public void getAllMpaTest(){
        List<Mpa> mpas = storage.getAllMpa();
        assertEquals(mpas.size(),5);
    }
    @Test
    public void getMpaByIdTest(){
        if(storage.getMpaById(1).isPresent()){
            assertEquals(storage.getMpaById(1).get().getName(),"G");
        }
    }

}
