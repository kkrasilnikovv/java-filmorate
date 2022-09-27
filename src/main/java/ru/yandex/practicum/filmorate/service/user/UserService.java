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





     void reset();
}
