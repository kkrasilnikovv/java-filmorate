package ru.yandex.practicum.filmorate.storage.jdbc;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    void reset();

    List<User>  getAllUsers();

    Optional<User> getUserById(Integer id);

    User addUser(User user);

    User updateUser(User user);




}