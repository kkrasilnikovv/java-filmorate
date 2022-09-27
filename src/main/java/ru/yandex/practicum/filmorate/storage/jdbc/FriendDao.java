package ru.yandex.practicum.filmorate.storage.jdbc;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendDao {
    List<User> getUserFriends(Integer id);

    void addFriend(Integer id, Integer friendId);

    void removeFriend(Integer id, Integer friendId);
    List<User> getUserCrossFriends(Integer id, Integer otherId);
}
