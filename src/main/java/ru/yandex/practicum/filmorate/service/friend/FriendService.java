package ru.yandex.practicum.filmorate.service.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendService {
    void addFriend(Integer id, Integer friendId);


    void removeFriend(Integer id, Integer friendId);


    List<User> getFriends(Integer id);


    List<User> getCrossFriends(Integer id, Integer userId);
}
