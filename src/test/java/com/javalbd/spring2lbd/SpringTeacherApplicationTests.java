package com.javalbd.spring2lbd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalbd.spring2lbd.component.AuthRole;
import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.entity.Teacher;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringTeacherApplicationTests {

    @Autowired private MockMvc mockMvc;

    @Test void getTeacherAllUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/all")
                        .header("role", AuthRole.STUDENT_ROLE))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test void getTeacherAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/all")
                        .header("role", AuthRole.TEACHER_ROLE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name", Is.is("Marcin")))
                .andDo(print());
    }

    @Test void getTeacher() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/getteacher")
                        .header("role", AuthRole.TEACHER_ROLE)
                        .param("id", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Is.is("Marcin")));
    }

    @Test void getTeacherClass() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/getteacherclass")
                        .header("role", AuthRole.TEACHER_ROLE)
                        .param("id", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", Is.is(SchoolSubject.LAW.toString())));
    }

    @Test void addTeacher() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/teacher/addteacher")
                        .header("role", AuthRole.TEACHER_ROLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Teacher("test", "test2", SchoolSubject.ALGEBRA))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test void deleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teacher/deleteteacher")
                        .header("role", AuthRole.TEACHER_ROLE)
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/all")
                        .header("role", AuthRole.TEACHER_ROLE))
                .andExpect(jsonPath("$.[1].id", Is.is(2)));
    }

}
