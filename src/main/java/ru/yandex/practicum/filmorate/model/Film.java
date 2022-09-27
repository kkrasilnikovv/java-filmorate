package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

}
