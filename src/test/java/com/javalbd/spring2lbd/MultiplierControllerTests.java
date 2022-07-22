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
class MultiplierControllerTests {

    @Autowired private MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;


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


}
