package com.javalbd.spring2lbd;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalbd.spring2lbd.dto.CreateUserDto;
import com.javalbd.spring2lbd.dto.UpdatePassUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin", authorities = "ACCESS_ALL")
class UserControllerTests {

    @Autowired private MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;


    /** UserController.class */
    @Test void getUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/getuser")
                        .param("username", "admin"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/deleteuser")
                        .param("username", "admin"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test void updatePasswordUser() throws Exception {
        UpdatePassUserDto updatePassUserDto = new UpdatePassUserDto();
        updatePassUserDto.setUsername("admin");
        updatePassUserDto.setNewPassword("user");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/updatepassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePassUserDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }



}
