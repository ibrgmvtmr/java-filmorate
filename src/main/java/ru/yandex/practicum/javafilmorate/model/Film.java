package ru.yandex.practicum.javafilmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.javafilmorate.controllers.validation.DateForFilms;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @PositiveOrZero
    private long id;

    @NotBlank(message = "Не правильное название фильма")
    private String name;

    @NotNull(message = "Отсутствует описание фильма")
    @Size(max = 200, message = "слишком длинное описание, больше 200 символов")
    private String description;

    @NotNull(message = "Отсутствует дата релиза фильма фильма")
    @DateForFilms
    private LocalDate releaseDate;

    @Min(value = 1, message = "Неправильная продолжительность фильма")
    private int duration;

    private Set<Long> likes = new HashSet<>();
}