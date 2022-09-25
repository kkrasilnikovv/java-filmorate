package ru.yandex.practicum.filmorate.storage.jdbc.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Repository("UserDaoImpl")
public class UserDaoImpl implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void reset() {
        final String sql = "DELETE FROM users ";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<User> getAllUsers() {
        final String sql = "Select * from users";
        final String sql1 = "SELECT FRIEND_ID  from FRIENDS where user_id = ?";
        List<User> users = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            User user = new User(rs.getInt("USER_ID"), rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getDate("BIRTHDAY").toLocalDate());
            SqlRowSet rs1 = jdbcTemplate.queryForRowSet(sql1, rs.getInt("USER_ID"));
            while (rs1.next()) {
                user.addFriend(rs1.getInt("FRIEND_ID"));
            }
            users.add(user);
        }
        return users;
    }

    @Override
    public User addUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        user.setId(simpleJdbcInsert.executeAndReturnKey(this.userToMap(user)).intValue());
        return user;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        final String sql = "SELECT * from USERS where USER_ID = ?";
        final String sql1 = "SELECT FRIEND_ID from FRIENDS WHERE USER_ID=?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        SqlRowSet rs1 = jdbcTemplate.queryForRowSet(sql1, id);
        if (rs.next()) {
            User user = new User(rs.getInt("USER_ID"),
                    rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getDate("BIRTHDAY").toLocalDate());
            while (rs1.next()) {
                user.addFriend(rs1.getInt("FRIEND_ID"));
            }

            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public User updateUser(User user) {
        final String sql = "update users set email = ?, login = ?, name = ?, " +
                "birthday = ?   where user_id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName()
                , user.getBirthday()
                , user.getId());
        return user;
    }

    @Override
    public List<User> getUserFriends(Integer id) {
        final String sql = "SELECT * From USERS where USER_ID IN (SELECT FRIEND_ID FROM FRIENDS where USER_ID = ?)";
        List<User> friends = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        while (rs.next()) {
            friends.add(new User(
                    rs.getInt("USER_ID"),
                    rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getDate("BIRTHDAY").toLocalDate()
            ));
        }
        return friends;
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        final String sql = "INSERT INTO FRIENDS(friend_id,user_id) VALUES(?,?)";
        jdbcTemplate.update(sql, friendId, id);
    }

    @Override
    public void removeFriend(Integer id, Integer friendId) {
        final String sql = "DELETE FROM FRIENDS WHERE USER_ID=? and FRIEND_ID=?";
        jdbcTemplate.update(sql, id, friendId);
    }

    public Map<String, Object> userToMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("EMAIL", user.getEmail());
        values.put("LOGIN", user.getLogin());
        values.put("NAME", user.getName());
        values.put("BIRTHDAY", user.getBirthday());

        return values;
    }

}
