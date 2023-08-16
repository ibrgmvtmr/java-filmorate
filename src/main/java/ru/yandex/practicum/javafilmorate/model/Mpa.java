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
    @NotBlank(message = "Описание возрастного ограничения не может быть пустым!")
    private String description;

    public Mpa(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
