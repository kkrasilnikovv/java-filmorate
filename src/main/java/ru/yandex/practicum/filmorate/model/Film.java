package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    final private Set<Integer> likes;

    private Integer id;
    @NotBlank
    private final String name;
    @NotBlank
    @Size(min=0,max = 200)
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @Min(1)
    @Positive
    private final int duration;
    @Builder
    public Film( String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
    }
    public void addLike(Integer filmId) {
        likes.add(filmId);
    }
    public void remoteLike(Integer userId) {
        likes.remove(userId);
    }

}
