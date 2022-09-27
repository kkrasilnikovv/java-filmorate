package ru.yandex.practicum.filmorate.storage.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.jdbc.LikeDao;
import java.util.ArrayList;
import java.util.List;
@Repository("LikeDaoImpl")
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public LikeDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public List<Film> getMostPopular(Integer count) {
        String sql = "select F.FILM_ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE,  F.DURATION, F.MPAA_ID, F.NAME " +
                "as MPAA_NAME from FILMS  F LEFT JOIN  LIKES L on F.FILM_ID  = L.FILM_ID " +
                "GROUP BY F.FILM_ID, L.USER_ID ORDER BY COUNT(L.USER_ID) desc LIMIT ?";
        List<Film> films = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,count);
        while (rs.next()){
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("release_date").toLocalDate(),
                    (rs.getInt("duration")),
                    new Mpa(rs.getInt("MPAA_ID"), rs.getString("MPAA_NAME")));
            films.add(film);
        }
        return films;
    }
    @Override
    public void addLike(Integer filmId, Integer userId){
        String sql = "insert into LIKES(USER_ID, FILM_ID) values (?, ?)";
        jdbcTemplate.update(sql,userId,filmId);
    }
    @Override
    public void removeLike(Integer filmId, Integer userId){
        String sql = "DELETE FROM LIKES WHERE USER_ID=? and FILM_ID=?";
        jdbcTemplate.update(sql,userId,filmId);
    }
    @Override
    public List<Integer> getLikesByFilm(Integer id){
        final String sql = "SELECT * FROM LIKES WHERE FILM_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getInt("USER_ID"), id);
    }
}
