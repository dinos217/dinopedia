package com.project.dinopedia;

import com.project.dinopedia.dtos.LoginRequestDto;
import com.project.dinopedia.dtos.UserDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class HomeControllerTest extends BasicTestConfig {

    @Ignore
    @Test
    public void registerTest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("Dino");
        userDto.setPassword("123");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void loginTest() throws Exception {

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("Dinos");
        loginRequestDto.setPassword("123");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequestDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andDo(print());
    }
}
