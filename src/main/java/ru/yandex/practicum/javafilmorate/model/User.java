package ru.yandex.practicum.javafilmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
public class User {

    @PositiveOrZero
    private Integer id;

    private String name;

    @NotBlank(message = "Отсутствует email")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "логин пуст")
    private String login;

    @NotNull(message = "Не указана дата рождения")
    @PastOrPresent(message = "Некорректная дата рождения")
    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();

    public User(Integer id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public User(Integer id, String name, String email, String login, LocalDate birthday, Set<Integer> friends) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        this.friends = friends;
    }
}