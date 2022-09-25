package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();


    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> findById(Integer id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public void reset() {
        films.clear();
    }

    @Override
    public Film addFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsValue(film)) {
            films.replace(film.getId(), film);
        }
        return film;
    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        return films.values().stream().sorted(Comparator.comparingInt(f -> -f.getLikes().size()))
                .limit(count).collect(Collectors.toList());
    }
    @Override
    public void addLike(Integer filmId, Integer userId){
        findById(filmId).get().addLike(userId);
    }
    @Override
    public void removeLike(Integer filmId, Integer userId){
        findById(filmId).get().remoteLike(userId);
    }


}
