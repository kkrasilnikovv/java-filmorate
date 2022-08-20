package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component

public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();


    @Override
    public void reset() {
        users.clear();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsValue(user)) {
            users.replace(user.getId(), user);
        }
        return user;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getUserFriends(Integer id) {
        return users.get(id).getFriends().stream()
                .map(users::get).collect(Collectors.toList());
    }


}
