package ru.yandex.practicum.javafilmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @PositiveOrZero
    private int id;

    @NotBlank(message = "Отсутствует email")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "логин пуст")
    private String login;

    private String name;

    @NotNull(message = "Не указана дата рождения")
    @PastOrPresent(message = "Некорректная дата рождения")
    private LocalDate birthday;
}