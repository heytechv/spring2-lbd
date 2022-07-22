package com.javalbd.spring2lbd;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalbd.spring2lbd.dto.CreateUserDto;
import com.javalbd.spring2lbd.dto.UpdatePassUserDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin", authorities = "ACCESS_ALL")
class SpringApplicationTests {

    @Autowired private MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;


    /** DecimalController.class */
    @Test void setDecimal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/decimal/setdecimal")
                        .param("decimalPlaces", "2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"decimalPlaces\": \"2\"}")
                        .header("decimalPlaces", "2"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test void getDecimal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/decimal/getdecimal"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /** MultiplierController.class */
    @Test void setMultiplier() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/multiplier/setmultiplier")
                        .param("multiplier","2"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test void getMultiplier() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/multiplier/getmultiplier"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /** NumberController.class */
    @Test void getCalculatedValues() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/number/getcalculatedvalues"))
                .andExpect(status().isOk())
                .andDo(print());
    }

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
