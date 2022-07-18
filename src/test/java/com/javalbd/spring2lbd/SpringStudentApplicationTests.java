package com.javalbd.spring2lbd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalbd.spring2lbd.entity.Student;
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
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/student/addstudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Student("test", "test2", 43))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test void editStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/student/editstudent")
                        .param("id", "1")
                        .param("surname", "edit")
                        .param("age", "32"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/all"))
                .andExpect(jsonPath("$.[1].surname", Is.is("edit")));
    }

    @Test void deleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/deletestudent")
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/all"))
                .andExpect(jsonPath("$.[1].id", Is.is(2)));
    }




}
