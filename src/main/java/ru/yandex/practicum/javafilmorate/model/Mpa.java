package ru.yandex.practicum.javafilmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mpa {
    @PositiveOrZero
    private Integer id;

    @NotBlank(message = "Название возрастного ограничения не может быть пустым!")
    private String name;

    private String description;

}
