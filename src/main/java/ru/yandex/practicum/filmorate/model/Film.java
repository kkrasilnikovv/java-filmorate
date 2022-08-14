package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {

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
}
