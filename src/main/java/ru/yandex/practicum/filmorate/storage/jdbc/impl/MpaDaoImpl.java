package ru.yandex.practicum.filmorate.storage.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.jdbc.MpaDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Mpa> getMpaById(Integer id) {
        final String sql = "SELECT * FROM MPAA where MPAA_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        List<Mpa> mpas=mappingMpa(rs);
        if(mpas.isEmpty()){
            return Optional.empty();
        }else {
            return Optional.of(mpas.get(0));
        }
    }

    @Override
    public List<Mpa> getAllMpa() {
        final String sql = "SELECT * FROM MPAA";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        List<Mpa> mpas=mappingMpa(rs);
        if(mpas.isEmpty()){
            return new ArrayList<>();
        }else {
            return mpas;
        }
    }
    private List<Mpa> mappingMpa(SqlRowSet rs){
        List<Mpa> mpas = new ArrayList<>();
        while (rs.next()){
            Mpa mpa= new Mpa(rs.getInt("MPAA_ID"),rs.getString("NAME") );
            mpas.add(mpa);
        }
        return mpas;
    }
}
