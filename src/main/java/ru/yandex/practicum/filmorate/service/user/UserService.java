package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService{
    private final UserStorage storage;
    private Integer id = 0;
    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }
    
    public List<User> getAllUsers() {
        log.debug("Получен запрос GET /users");
        return storage.getAllUsers();
    }

    
    public User findById(Integer id) {
        return storage.findById(id).orElseThrow(() -> new NotFoundException("Пользователь с id "+id+" не найден."));
    }

    
    public User createUser(User user) {
        isValid(user);
        createUserId(user);
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Получен запрос POST. Передан обьект {}", user);
        return storage.addUser(user);
    }

    
    public User updateUser(User user) {
        isValid(user);
        if(storage.findById(user.getId()).isPresent()){
            log.debug("Пользователь {} обновлен",user);
            return storage.updateUser(user);
        }else {
            log.error("Передан запрос PUT с некорректным данными пользователя{}", user);
            throw new NotFoundException("Такого пользователя не существует.");
        }


    }

    
    public void addFriend(Integer id, Integer friendId) {
        findById(friendId).addFriend(id);
        findById(id).addFriend(friendId);
        log.debug("Пользователь {} добавлен в друзья к {}",friendId,id);
    }

    
    public void removeFriend(Integer id, Integer userId) {
        findById(id).getFriends().remove(userId);
    }

    
    public List<User> getFriends(Integer id) {
        if(storage.findById(id).isPresent()) {
            return storage.getUserFriends(id);
        }else {
            throw new NotFoundException("Не найден пользователь с id:" + id);
        }
    }

    
    public List<User> getCrossFriends(Integer id, Integer userId) {
        User firstUser = findById(id);
        User secondUser = findById(userId);
        log.debug("Получен список общих друзей {} с {}",id,userId);
        return firstUser.getFriends().stream().filter(secondUser.getFriends()::contains).map(this::findById)
                .collect(Collectors.toList());
    }

    
    public void reset() {
        log.debug("Хранилище пользователей очищено");
        storage.reset();
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
