package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

 public interface UserService {
     List<User> getAllUsers();


     User getUserById(Integer id);


     User createUser(User user);


     User updateUser(User user);





     void reset();
}
