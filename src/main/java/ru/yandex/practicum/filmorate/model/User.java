package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @PastOrPresent
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();

    public void addFriend(Integer friendId) {
        friends.add(friendId);
    }

}
