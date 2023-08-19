package ru.yandex.practicum.javafilmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    @Test
    @DisplayName("Получение списка всех фильмов")
    public void getFilms_ValidDataTest() throws Exception {
        when(filmService.getFilms()).thenReturn(Collections.singletonList(
                Film.builder()
                        .id(1)
                        .name("Film 1")
                        .description("Description")
                        .releaseDate(LocalDate.of(2022, 1, 1))
                        .duration(120)
                        .genres(Collections.emptyList())
                        .likes(Collections.emptyList())
                        .build()
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение фильма по ID")
    public void getFilm_ValidDataTest() throws Exception {
        Film film = Film.builder()
                .id(1)
                .name("Film 1")
                .description("Description")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(120)
                .genres(Collections.emptyList())
                .likes(Collections.emptyList())
                .build();

        when(filmService.getFilm(film.getId())).thenReturn(film);

        mockMvc.perform(MockMvcRequestBuilders.get("/films/{filmId}", film.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление информации о фильме")
    public void updateFilm_ValidDataTest() throws Exception {
        Film film = Film.builder()
                .id(1)
                .name("Film 1")
                .description("Description")
                .releaseDate(LocalDate.of(2022, 1, 1))
                .duration(120)
                .genres(Collections.emptyList())
                .likes(Collections.emptyList())
                .build();

        when(filmService.update(film)).thenReturn(film);

        mockMvc.perform(MockMvcRequestBuilders.put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление лайка к фильму")
    public void addLike_ValidDataTest() throws Exception {
        int filmId = 1;
        int userId = 1;

        mockMvc.perform(MockMvcRequestBuilders.put("/films/{filmId}/like/{userId}", filmId, userId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление лайка из фильма")
    public void deleteLike_ValidDataTest() throws Exception {
        int filmId = 1;
        int userId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/films/{filmId}/like/{userId}", filmId, userId))
                .andExpect(status().isOk());
    }
}
