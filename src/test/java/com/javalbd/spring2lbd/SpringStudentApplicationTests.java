package com.javalbd.spring2lbd;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Zad 13 (1/2) */
@SpringBootTest
@AutoConfigureMockMvc
class SpringStudentApplicationTests {

    @Autowired private MockMvc mockMvc;


    @Test void getStudentAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name", Is.is("Maciek")));
    }

    @Test void getStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/getstudent?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Is.is("Marek")));
    }

    @Test void addStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/student/addstudent")
                        .param("name", "imie")
                        .param("surname", "nazw")
                        .param("age", "23")
                )
                .andDo(print());
    }

}
