package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.javafilmorate.model.Mpa;
import ru.yandex.practicum.javafilmorate.service.MpaService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MpaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MpaService mpaService;

    @Test
    @DisplayName("Получение списка всех рейтингов")
    public void getMpas_ValidDataTest() throws Exception {
        when(mpaService.getMpas()).thenReturn(Collections.singletonList(
                Mpa.builder()
                        .id(1)
                        .name("Mpa 1")
                        .description("Description")
                        .build()
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/mpa"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение рейтинга по ID")
    public void getMpa_ValidDataTest() throws Exception {
        Mpa mpa = Mpa.builder()
                .id(1)
                .name("Mpa 1")
                .description("Description")
                .build();

        when(mpaService.getMpa(mpa.getId())).thenReturn(mpa);

        mockMvc.perform(MockMvcRequestBuilders.get("/mpa/{id}", mpa.getId()))
                .andExpect(status().isOk());
    }
}
