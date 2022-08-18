package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;

@Service
public class UserService{
    private final UserStorage storage;
    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }
    
    public List<User> getAllUsers() {
        return storage.getAllUsers();
    }

    
    public User findById(Integer id) {
        return storage.findById(id);
    }

    
    public User createUser(User user) {
        return storage.addUser(user);
    }

    
    public User updateUser(User user) {
        return storage.updateUser(user);
    }

    
    public void addFriend(Integer id, Integer friendId) {
        storage.findById(friendId).addFriend(id);
        storage.findById(id).addFriend(friendId);
    }

    
    public void removeFriend(Integer id, Integer userId) {
        storage.findById(id).getFriends().remove(userId);
    }

    
    public List<User> getFriends(Integer id) {
        return storage.getUserFriends(id);
    }

    
    public List<User> getCrossFriends(Integer id, Integer userId) {
        return storage.getUserCrossFriends(id, userId);
    }

    
    public void reset() {
        storage.reset();
    }
}
