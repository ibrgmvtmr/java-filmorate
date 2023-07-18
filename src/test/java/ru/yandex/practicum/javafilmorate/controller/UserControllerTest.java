package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.javafilmorate.storage.inmemory.InMemoryUserStorage;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InMemoryUserStorage inMemoryUserStorage;

    @Test
    @DisplayName("Добавление нового пользователя - login : null")
    public void methodPost_NewUserInvalid_LoginNullTest() throws Exception {
        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"name\":\"test\"," +
                                        "\"birthday\":\"2023-01-01\"," +
                                        "\"email\":\"test@gmail.com\"" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Добавление нового пользователя - email : null")
    public void methodPost_NewUserInvalid_EmailNullTest() throws Exception {

        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"name\":\"test\"," +
                                        "\"birthday\":\"2023-01-01\"," +
                                        "\"login\":\"test\"" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Добавление нового пользователя - email : incorrect")
    public void methodPost_NewUserInvalid_IncorrectEmailTest() throws Exception {
        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"email\":\"incorrect.email\"," +
                                        "\"name\":\"test\"," +
                                        "\"birthday\":\"2023-01-01\"," +
                                        "\"login\":\"test\"" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Добавление нового пользователя - birthday : null")
    public void methodPost_NewUserInvalid_BirthdayNullTest() throws Exception {
        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"name\":\"test\"," +
                                        "\"login\":\"test\"," +
                                        "\"email\":\"test@gmail.com\"" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Добавление нового пользователя")
    public void methodPost_NewUserValidTest() throws Exception {
        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"name\":\"test\"," +
                                        "\"login\":\"test\"," +
                                        "\"birthday\":\"2020-01-01\"," +
                                        "\"email\":\"test@gmail.com\"" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }
}