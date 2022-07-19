package com.javalbd.spring2lbd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalbd.spring2lbd.component.AuthRole;
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

@SpringBootTest
@AutoConfigureMockMvc
class SpringStudentApplicationTests {

    @Autowired private MockMvc mockMvc;

    @Test void getStudentAllUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/all"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test void getStudentAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/all")
                        .header("role", AuthRole.STUDENT_ROLE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name", Is.is("Maciek")))
                .andDo(print());
    }

    @Test void getStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/getstudent?id=3")
                        .header("role", AuthRole.STUDENT_ROLE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Is.is("Robert")));
    }

    @Test void addStudent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/student/addstudent")
                        .header("role", AuthRole.STUDENT_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Student("test", "test2", 43))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test void editStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/student/editstudent")
                        .header("role", AuthRole.STUDENT_ROLE)
                        .param("id", "3")
                        .param("surname", "edit")
                        .param("age", "32"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test void deleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/deletestudent")
                        .header("role", AuthRole.STUDENT_ROLE)
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/all")
                        .header("role", AuthRole.STUDENT_ROLE))
                .andExpect(jsonPath("$.[1].id", Is.is(2)));
    }

}
