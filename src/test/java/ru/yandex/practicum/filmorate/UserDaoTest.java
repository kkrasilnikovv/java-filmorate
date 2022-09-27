package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.UserDaoImpl;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDaoTest {
    private final UserDaoImpl storage;

    @BeforeEach
    public void updateDatabase() {
        storage.reset();
        storage.addUser(new User("Ivan", "mail@mail.ru", "CoolMan",
                LocalDate.of(2003, 03, 31)));
        storage.addUser(new User("Sergey", "yandex@mail.ru", "BadMan",
                LocalDate.of(2004, 02, 26)));
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = storage.getAllUsers();
        assertTrue(users.size() >= 2);
    }

    @Test
    public void getUserByIdTest() {
        if (storage.getUserById(1).isPresent()) {
            assertEquals(storage.getUserById(1).get().getId(),1);
        }
    }
    @Test
    public void updateUserTest() {
        User test = new User(1,"Oleg", "mail@mail.ru", "CoolMan",
                LocalDate.of(2003, 03, 31));
        storage.updateUser(test);
        if (storage.getUserById(1).isPresent()) {
            assertEquals(storage.getUserById(1).get().getName(),"Oleg");
        }
    }

    @Test
    public void addUserTest() {
        User test = new User("Alex", "sasha@mail.ru", "Man",
                LocalDate.of(2000, 07, 23));
        storage.addUser(test);
        if (storage.getUserById(3).isPresent()) {
            assertEquals(storage.getUserById(3).get().getName(),"Alex");
        }
    }


}
