package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;



    public User(Integer id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public User(String name, String email, String login, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
