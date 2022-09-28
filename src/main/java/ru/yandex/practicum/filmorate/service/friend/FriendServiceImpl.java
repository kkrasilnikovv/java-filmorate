package ru.yandex.practicum.filmorate.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.jdbc.FriendDao;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{
    private final FriendDao storage;
    private final UserService serviceUser;

    @Override
    public void addFriend(Integer id, Integer friendId) {
        serviceUser.getUserById(id);
        serviceUser.getUserById(friendId);
        storage.addFriend(id, friendId);
        log.debug("Пользователь {} добавлен в друзья к {}", friendId, id);
    }

    @Override
    public void removeFriend(Integer id, Integer friendId) {
        serviceUser.getUserById(id);
        serviceUser.getUserById(friendId);
        storage.removeFriend(id, friendId);
        log.debug("Пользователь {} удален из друзей {}", friendId, id);
    }

    @Override
    public List<User> getFriends(Integer id) {
        serviceUser.getUserById(id);
        return storage.getUserFriends(id);

    }

    @Override
    public List<User> getCrossFriends(Integer id, Integer userId) {
        return storage.getUserCrossFriends(id,userId);
    }
}
