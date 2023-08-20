package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.service.GenreService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Получение списка всех жанров")
    public void getGenres_ValidDataTest() throws Exception {
        when(genreService.getGenres()).thenReturn(Collections.singletonList(
                Genre.builder()
                        .id(1)
                        .name("Genre 1")
                        .build()
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение жанра по ID")
    public void getGenre_ValidDataTest() throws Exception {
        Genre genre = Genre.builder()
                .id(1)
                .name("Genre 1")
                .build();

        when(genreService.getGenre(genre.getId())).thenReturn(genre);

        mockMvc.perform(MockMvcRequestBuilders.get("/genres/{id}", genre.getId()))
                .andExpect(status().isOk());
    }
}
