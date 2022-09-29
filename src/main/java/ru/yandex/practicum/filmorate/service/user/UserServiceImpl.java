package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.jdbc.UserDao;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao storage;

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
    public void reset() {
        log.debug("Хранилище пользователей очищено");
        storage.reset();
    }
}
