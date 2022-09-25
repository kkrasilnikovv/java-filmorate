package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {

    private Integer id;
    @NotBlank
    private final String name;
    @NotBlank
    @Size(min = 0, max = 200)
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @Min(1)
    @Positive
    private final int duration;
    private List<Genre> genres=new ArrayList<>();
    private Integer rate;
    @NotNull
    private Mpa mpa;
    private final Set<Integer> likes=new HashSet<>();

@Builder
    public Film(Integer id, String name, String description, LocalDate releaseDate, int duration,
                Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }


    public void addLike(Integer filmId) {
        likes.add(filmId);
        rate=likes.size();
    }

    public void remoteLike(Integer userId) {
        likes.remove(userId);
        rate=likes.size();
    }

}
