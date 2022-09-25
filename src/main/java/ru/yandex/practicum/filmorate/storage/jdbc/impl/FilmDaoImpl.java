package ru.yandex.practicum.filmorate.storage.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.jdbc.FilmGenreDao;

import java.sql.Date;
import java.util.*;


@Repository("FilmDaoImpl")
public class FilmDaoImpl implements FilmStorage{
    private final JdbcTemplate jdbcTemplate;
    private final FilmGenreDao filmGenreDao;


    private static final String SELECT_ALL = "select FILMS.FILM_ID, FILMS.NAME, FILMS.DESCRIPTION, FILMS.RELEASE_DATE,"
            + " FILMS.DURATION, FILMS.MPAA_ID, fg.GENRE_ID as GID, G2.NAME as GNAME, MPAA.NAME as MPAA_NAME from films " +
            "join MPAA on FILMS.MPAA_ID = MPAA.MPAA_ID join  FILMS_GENRES fg on FILMS.FILM_ID = fg.FILM_ID left join  " +
            "GENRES as G2 on fg.GENRE_ID = G2.GENRE_ID order by FILM_ID ";
    private static final String SELECT_BY_ID = "select f.FILM_ID as FILM_ID, f.NAME , f.DESCRIPTION, f.RELEASE_DATE, " +
            "f.DURATION, f.MPAA_ID, M2.NAME as MPAA_NAME, fg.GENRE_ID as GID, G2.NAME as GNAME, L.USER_ID as `LIKE` " +
            "from films f  left join  FILMS_GENRES fg " +
            "on f.FILM_ID = fg.FILM_ID left join  GENRES as G2 on fg.GENRE_ID = G2.GENRE_ID left join LIKES L " +
            "on f.FILM_ID = L.FILM_ID " +
            "left join MPAA M2 on f.MPAA_ID = M2.MPAA_ID " +
            "where f.FILM_ID = ?";

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate, FilmGenreDao filmGenreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreDao = filmGenreDao;
    }
    @Override
    public void reset() {
        final String sql = "DROP TABLE users CASCADE;";
        jdbcTemplate.update(sql);
    }
    @Override
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ALL);
        while (rs.next()) {
            List<Genre> genres = new ArrayList<>();
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("release_date").toLocalDate(),
                    (rs.getInt("duration")),
                    new Mpa(rs.getInt("MPAA_ID"), rs.getString("MPAA_NAME"))
            );
            if (rs.getString("GNAME") != null) {
                genres.add(new Genre(rs.getInt("GID"), rs.getString("GNAME")));
            }
            film.setGenres(genres);
            films.add(film);
        }
        return films;
    }

    @Override
    public Optional<Film> findById(Integer id) {
        Film film = null;
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_BY_ID, id);
        if (rs.next()) {
            Set<Genre> genres = new HashSet<>();
            Set<Integer> likes = new HashSet<>();

            film = Film.builder()
                    .description(rs.getString("description"))
                    .id(rs.getInt("film_id"))
                    .name(rs.getString("NAME"))
                    .releaseDate((rs.getDate("release_date").toLocalDate()))
                    .duration(rs.getInt("duration"))
                    .build();

            film.setMpa(new Mpa(rs.getInt("MPAA_ID"),
                    rs.getString("MPAA_NAME")));

            do {
                if (rs.getString("GNAME") != null) {
                    genres.add(new Genre(rs.getInt("GID"), rs.getString("GNAME")));
                }
                Integer l;
                if ((l = rs.getInt("LIKE")) != 0) {
                    likes.add(l);
                }
            } while (rs.next());
            film.setGenres(new ArrayList<>(genres));
            film.getLikes().addAll(likes);
            getLikesByFilm(film);
            if (film.getGenres().isEmpty()) {
                film.setGenres(null);
            }
            return Optional.of(film);
        }
        return Optional.empty();
    }

    @Override
    public Film addFilm(Film film) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("FILM_ID");

        film.setId(simpleJdbcInsert.executeAndReturnKey(this.filmToMap(film)).intValue());

        filmGenreDao.updateAllGenreByFilm(film);

        return film;
    }

    private Map<String, Object> filmToMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("NAME", film.getName());
        values.put("DESCRIPTION", film.getDescription());
        values.put("RELEASE_DATE", film.getReleaseDate());
        values.put("DURATION", film.getDuration());
        if (film.getMpa() != null) {
            values.put("MPAA_ID", film.getMpa().getId());
        }

        return values;
    }

    private void getLikesByFilm(Film film) {
        final String sql = "SELECT * FROM LIKES WHERE FILM_ID = ?";
        List<Integer> likes = jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getInt("USER_ID"), film.getId());
        film.getLikes().addAll(likes);
    }



    @Override
    public Film updateFilm(Film film) {
        final String sql = "update FILMS set NAME = ?, DESCRIPTION = ?,RELEASE_DATE = ?, DURATION = ?, MPAA_ID = ?   where FILM_ID = ?";
        int count = jdbcTemplate.update(sql
                , film.getName()
                , film.getDescription()
                , Date.valueOf(film.getReleaseDate())
                , film.getDuration()
                , film.getMpa().getId()
                , film.getId());

        if (count == 1) {
            filmGenreDao.updateAllGenreByFilm(film);
        }
        return film;
    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        String sql = "select F.FILM_ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE,  F.DURATION, F.MPAA_ID, F.NAME as MPAA_NAME " +
                "from FILMS  F LEFT JOIN  LIKES L on F.FILM_ID  = L.FILM_ID " +
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
            getLikesByFilm(film);
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
}
