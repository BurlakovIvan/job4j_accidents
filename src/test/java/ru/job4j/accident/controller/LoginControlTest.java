package ru.job4j.accident.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accident.Main;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class LoginControlTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginControl controller;

    @Test
    @WithMockUser
    public void testLoginPageWithError() throws Exception {
        this.mockMvc.perform(get("/login")
                        .param("error", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content()
                        .string(containsString("Username or Password is incorrect!!!")));
    }

    @Test
    @WithMockUser
    public void testLoginPageWithLogout() throws Exception {
        this.mockMvc.perform(get("/login")
                        .param("logout", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content()
                        .string(containsString("You have been successfully logged out!!!")));
    }

    @Test
    @WithMockUser
    public void testLogout() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}