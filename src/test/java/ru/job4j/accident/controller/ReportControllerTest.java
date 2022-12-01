package ru.job4j.accident.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accident.Main;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportController controller;

    @Test
    @WithMockUser
    public void testReports() throws Exception {
        this.mockMvc.perform(get("/reports"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reports"));
    }

    @Test
    @WithMockUser
    public void testAddReport() throws Exception {
        this.mockMvc.perform(get("/addReport"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createReport"));
    }

    @Test
    @WithMockUser
    public void testFormUpdateReportSuccess() throws Exception {
        this.mockMvc.perform(get("/formUpdateReport")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("formUpdateReport"));
    }

    @Test
    @WithMockUser
    public void testFormUpdateReportFail() throws Exception {
        this.mockMvc.perform(get("/formUpdateReport")
                        .param("id", "-1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reports"));
    }
}