package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    @Override
    public void reset() {
        users.clear();
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Получен запрос GET /users");
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        isValid(user);
        createUserId(user);
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Получен запрос POST. Передан обьект {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        isValid(user);
        if (users.containsKey(user.getId())) {
            if (!users.containsValue(user)) {
                users.replace(user.getId(), user);
                log.debug("Пользователь обновлен");
            } else {
                log.debug("Пользователь уже создан");
            }
            return user;
        } else {
            log.error("Передан запрос PUT с некорректным данными пользователя{}", user);
            throw new NotFoundException("Такого пользователя не существует.");
        }
    }

    @Override
    public User findById(Integer id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new NotFoundException("Не найден пользователь с id:" + id);
        }
    }

    @Override
    public List<User> getUserFriends(Integer id) {
        if (users.containsKey(id)) {
            return users.get(id).getFriends().stream()
                    .map(users::get).collect(Collectors.toList());
        } else {
            throw new NotFoundException("Не найден пользователь с id:" + id);
        }
    }


    @Override
    public List<User> getUserCrossFriends(Integer id, Integer userId) {
        User firstUser = findById(id);
        User secondUser = findById(userId);
        List<User> crossFriends = new ArrayList<>();
        firstUser.getFriends().stream()
                .filter(idUser -> secondUser.getFriends().contains(idUser))
                .forEach(idUser -> crossFriends.add(findById(idUser)));
        return crossFriends;
    }

    private void createUserId(User user) {
        id++;
        user.setId(id);
    }

    private void isValid(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные пользователя.");
        }
    }
}
