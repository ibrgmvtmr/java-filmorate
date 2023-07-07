package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.javafilmorate.controllers.UserController;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;
    @Test
    @DisplayName("Добавление нового пользователя - login : null")
    public void methodPost_NewUserValidFalse_LoginNullTest() throws Exception {
        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"name\":\"test\"," +
                                        "\"birthday\":\"2023-01-01\"," +
                                        "\"email\":test@gmail.com" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Добавление нового пользователя - email : null")
    public void methodPost_NewUserValidFalse_EmailNullTest() throws Exception {

        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"name\":\"test\"," +
                                        "\"birthday\":\"2023-01-01\"," +
                                        "\"login\":test" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Добавление нового пользователя - email : incorrect")
    public void methodPost_NewUserValidFalse_IncorrectEmailTest() throws Exception {
        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"email\":\"incorrect\"," +
                                        "\"name\":\"test\"," +
                                        "\"birthday\":\"2023-01-01\"," +
                                        "\"login\":test" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));

    }

    @Test
    @DisplayName("Добавление нового пользователя - name : blank")
    public void testCreateUser_WithBlankName() throws Exception {
        User user = new User(1, "test@example.com", "testlogin", "", LocalDate.now());
        User expectedUser = new User(1, "test@example.com", "testlogin", "testlogin", LocalDate.now());

        when(userController.createUser(any(User.class))).thenReturn(expectedUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"email\":\"test@example.com\",\"login\":\"testlogin\",\"name\":\"\",\"birthday\":\"2000-01-01\"}"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Добавление нового пользователя - birthday : null")
    public void methodPost_NewUserValidFalse_birthdayNullTest() throws Exception{
        mockMvc.perform(post("/users")
                        .content(
                                "{" +
                                        "\"name\":\"test\"," +
                                        "\"login\":\"test\"," +
                                        "\"email\":\"test@gmail.com\"" +
                                        "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
    @Test
    @DisplayName("Добавление нового пользователя")
    public void methodPost_NewUserValidTrue() throws Exception{
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
                .andExpect(status().is(200));
    }
}
