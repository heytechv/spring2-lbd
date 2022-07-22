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
class RegisterControllerTests {

    @Autowired private MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;


    /** RegisterController.class */
    @Test void register() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setUsername("user");
        createUserDto.setPassword("user");
        createUserDto.setAuthorities(new String[] {"ACCESS_ALL"});

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
