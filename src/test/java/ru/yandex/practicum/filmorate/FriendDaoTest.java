package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.FriendDaoImpl;
import ru.yandex.practicum.filmorate.storage.jdbc.impl.UserDaoImpl;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendDaoTest {
    private final FriendDaoImpl storage;
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
    }
    @Test
    public void addFriendAndGetUserFriendsTest() {
        storage.addFriend(1,3);
        storage.addFriend(2,3);
        assertEquals(storage.getUserFriends(1).get(0).getId(),3);
    }

    @Test
    public void getUserCrossFriendsTest(){
        assertEquals(storage.getUserCrossFriends(1,2).get(0).getId(),3);
    }
    @Test
    public void removeFriendTest() {
        storage.removeFriend(1,3);
        assertTrue(storage.getUserFriends(1).isEmpty());
    }
}
