package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.stream.Collectors;

 public interface UserService {
     List<User> getAllUsers();


     User getUserById(Integer id);


     User createUser(User user);


     User updateUser(User user);


     void addFriend(Integer id, Integer friendId);


     void removeFriend(Integer id, Integer friendId);


     List<User> getFriends(Integer id);


     List<User> getCrossFriends(Integer id, Integer userId);


     void reset();
}
