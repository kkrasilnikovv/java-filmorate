package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserStorage storage;
    private Integer id = 0;

    @Autowired
    public UserServiceImpl(@Qualifier("UserDaoImpl") UserStorage storage) {
        this.storage = storage;
    }
    @Override
    public List<User> getAllUsers() {
        log.debug("Получен запрос GET /users");
        return storage.getAllUsers();
    }

    @Override
    public User getUserById(Integer id) {
        return storage.getUserById(id).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + id + " не найден."));
    }

    @Override
    public User createUser(User user) {
        createUserId(user);
        log.debug("Получен запрос POST. Передан обьект {}", user);
        return storage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        getUserById(user.getId());
        log.debug("Пользователь {} обновлен", user);
        return storage.updateUser(user);

    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        getUserById(id);
        getUserById(friendId);
        storage.addFriend(id, friendId);
        log.debug("Пользователь {} добавлен в друзья к {}", friendId, id);
    }

    @Override
    public void removeFriend(Integer id, Integer friendId) {
        getUserById(id);
        getUserById(friendId);
        storage.removeFriend(id, friendId);
        log.debug("Пользователь {} удален из друзей {}", friendId, id);
    }

    @Override
    public List<User> getFriends(Integer id) {
        getUserById(id);
        return storage.getUserFriends(id);

    }

    @Override
    public List<User> getCrossFriends(Integer id, Integer userId) {
        User firstUser = getUserById(id);
        User secondUser = getUserById(userId);
        log.debug("Получен список общих друзей {} с {}", id, userId);
        return firstUser.getFriends().stream().filter(secondUser.getFriends()::contains).map(this::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        log.debug("Хранилище пользователей очищено");
        storage.reset();
    }

    private void createUserId(User user) {
        id++;
        user.setId(id);
    }


}
