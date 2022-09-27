package ru.yandex.practicum.filmorate.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;


    @GetMapping
    public List<User> gettingAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return service.getUserById(userId);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        isValid(user);
        return service.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        isValid(user);
        return service.updateUser(user);
    }



    private void isValid(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Некорректные данные пользователя.");
        }
    }

}
