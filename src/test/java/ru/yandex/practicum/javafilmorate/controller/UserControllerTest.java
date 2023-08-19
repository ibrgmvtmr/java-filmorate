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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Создание нового пользователя - невалидные данные")
    public void createUser_InvalidDataTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new User())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createUser_ValidDataTest() throws Exception {
        User user = User.builder()
                .id(1)
                .name("Test User")
                .email("test@example.com")
                .login("testlogin")
                .birthday(LocalDate.of(1990, 1, 1))
                .friends(Collections.emptySet())
                .build();

        User user2 = User.builder()
                    .id(2)
                    .name("Test User")
                    .email("test@example.com")
                    .login("testlogin")
                    .birthday(LocalDate.of(1990, 1, 1))
                    .friends(Collections.emptySet())
                    .build();


        when(userService.create(user)).thenReturn(user,user2);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Обновление пользователя - невалидные данные")
    public void updateUser_InvalidDataTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new User())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Обновление пользователя")
    public void updateUser_ValidDataTest() throws Exception {
        User user = User.builder()
                .id(1)
                .name("Updated User")
                .email("updated@example.com")
                .login("updatedlogin")
                .birthday(LocalDate.of(1995, 5, 5))
                .friends(Collections.emptySet())
                .build();

        when(userService.update(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение списка всех пользователей")
    public void getUsers_ValidDataTest() throws Exception {
        when(userService.getUsers()).thenReturn(Collections.singletonList(
                User.builder()
                        .id(1)
                        .name("User 1")
                        .email("user1@example.com")
                        .login("user1")
                        .birthday(LocalDate.of(1990, 1, 1))
                        .friends(Collections.emptySet())
                        .build()
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    public void getUser_ValidDataTest() throws Exception {
        User user = User.builder()
                .id(1)
                .name("User 1")
                .email("user1@example.com")
                .login("user1")
                .birthday(LocalDate.of(1990, 1, 1))
                .friends(Collections.emptySet())
                .build();

        when(userService.getUser(user.getId())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление друга пользователю")
    public void addFriend_ValidDataTest() throws Exception {
        doNothing().when(userService).addFriend(1, 2);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/friends/{friendId}", 1, 2))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Удаление друга пользователя")
    public void removeFriend_ValidDataTest() throws Exception {
        doNothing().when(userService).removeFriend(1, 2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}/friends/{friendId}", 1, 2))
                .andExpect(status().isOk());
    }


}