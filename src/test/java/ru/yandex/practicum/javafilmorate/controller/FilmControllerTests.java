//package ru.yandex.practicum.javafilmorate.controller;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class FilmControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @DisplayName("Добавление нового фильма - duration : 0")
//    public void methodPost_NewFilmValidFalse_DurationZeroTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/films")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"\",\"description\":\"Film Description\",\"releaseDate\":\"2023-07-05\",\"duration\":0}"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("Добавление нового фильма - name : null")
//    public void methodPost_NewFilmValidFalse_NameNullTest() throws Exception {
//        mockMvc.perform(post("/films")
//                        .content(
//                                "{" +
//                                        "\"description\":\"test\"," +
//                                        "\"releaseDate\":\"2023-01-01\"," +
//                                        "\"duration\":135" +
//                                        "}"
//                        )
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(400));
//    }
//
//    @Test
//    @DisplayName("Добавление нового фильма - description : null")
//    public void methodPost_NewFilmValidFalse_DescriptionNullTest() throws Exception {
//        mockMvc.perform(post("/films")
//                        .content(
//                                "{" +
//                                        "\"name\":\"test\"," +
//                                        "\"releaseDate\":\"2023-01-01\"," +
//                                        "\"duration\":135" +
//                                        "}"
//                        )
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(400));
//
//    }
//
//    @Test
//    @DisplayName("Добавление нового фильма - releaseDate : incorrect")
//    public void methodPost_NewFilmValidFalse_InvalidReleaseDate() throws Exception {
//        mockMvc.perform(post("/films")
//                        .content(
//                                "{" +
//                                        "\"name\":\"test\"," +
//                                        "\"description\":\"test\"," +
//                                        "\"releaseDate\":\"1800-01-01\"," +
//                                        "\"duration\":135" +
//                                        "}"
//                        )
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(400));
//    }
//
//    @Test
//    @DisplayName("Добавление нового фильма")
//    public void methodPost_NewFilmValidTrue() throws Exception {
//        mockMvc.perform(post("/films")
//                        .content(
//                                "{" +
//                                        "\"name\":\"test\"," +
//                                        "\"description\":\"test\"," +
//                                        "\"releaseDate\":\"2020-01-01\"," +
//                                        "\"duration\":135" +
//                                        "}"
//                        )
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200));
//    }
//}