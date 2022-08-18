package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage{

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final static LocalDate BIRTH_MOVIE = LocalDate.of(1895, 12, 28);
    private Integer id=0;
    @Override
    public List<Film> getAllFilms() {
        log.debug("Получен запрос GET /films.");
        return new ArrayList<>(films.values());
    }
    @Override
    public Film findById(Integer id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundException("Не найден фильм с id:"+id);
        }
    }
    @Override
    public void reset(){
        films.clear();
    }
    @Override
    public Film addFilm(Film film){
        isValid(film);
        createFilmId(film);
        films.put(film.getId(),film);
        log.debug("Получен запрос POST. Передан обьект {}", film);
        return film;
    }
    @Override
    public Film updateFilm(Film film){
        isValid(film);
        if (films.containsKey(film.getId())) {
            if (!films.containsValue(film)) {
                films.replace(film.getId(), film);
                log.debug("Фильм обновлен");
            } else {
                log.debug("Фильм уже загружен.");
            }
            return film;
        } else {
            log.error("Передан запрос PUT с некорректным данными фильима {}.", film);
            throw new NotFoundException("Такого фильма не существует.");
        }
    }
    @Override
    public List<Film> getMostPopular(Integer count) {
        if(count>0){
            return films.values().stream().sorted(Comparator.comparingInt(f -> -f.getLikes().size()))
                    .limit(count).collect(Collectors.toList());
        }else {
            throw new ValidationException("Передан некорректный параметр.");
        }

    }
    private void createFilmId(Film film){
        id++;
        film.setId(id);
    }
    private void isValid(Film film){
        if (film.getReleaseDate().isBefore(BIRTH_MOVIE)) {
            log.error("Передан запрос POST с некорректным данными фильима {}.", film);
            throw new ValidationException("Некорректные данные фильма.");
        }
    }

}
