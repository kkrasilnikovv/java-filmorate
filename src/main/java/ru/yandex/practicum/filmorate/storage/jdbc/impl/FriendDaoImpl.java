package ru.yandex.practicum.filmorate.storage.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.jdbc.FriendDao;

import java.util.ArrayList;
import java.util.List;
@Repository("FriendDaoImpl")
public class FriendDaoImpl implements FriendDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<User> getUserFriends(Integer id) {
        final String sql = "SELECT * From USERS where USER_ID IN (SELECT FRIEND_ID FROM FRIENDS where USER_ID = ?)";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        return mappingFriends(rs);
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
    @Override
    public List<User> getUserCrossFriends(Integer id, Integer otherId) {
        final String sql = "SELECT * From USERS where USER_ID IN (SELECT FRIEND_ID " +
                "FROM FRIENDS where USER_ID = ?)" +
                "AND USER_ID IN (SELECT FRIEND_ID FROM FRIENDS where USER_ID = ?)";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,id,otherId);
        return mappingFriends(rs);
    }
    private List<User> mappingFriends(SqlRowSet rs){
        List<User> friends = new ArrayList<>();
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
}
