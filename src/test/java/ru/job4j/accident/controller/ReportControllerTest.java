package ru.job4j.accident.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accident.Main;
import ru.job4j.accident.model.Report;
import ru.job4j.accident.service.ReportService;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService service;

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
    public void testFormUpdateReportFail() throws Exception {
        this.mockMvc.perform(get("/formUpdateReport")
                        .param("id", "-1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reports"));
    }

    @Test
    @WithMockUser
    public void testFormCreate() throws Exception {
        this.mockMvc.perform(post("/saveReport")
                        .param("id", "1")
                        .param("name", "Report")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reports"));
        ArgumentCaptor<Report> argument = ArgumentCaptor.forClass(Report.class);
        verify(service).create(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
        assertThat(argument.getValue().getName()).isEqualTo("Report");
    }

    @Test
    @WithMockUser
    public void testFormUpdate() throws Exception {
        service.create(new Report(1, "null", 1));
        this.mockMvc.perform(post("/updateReport")
                        .param("id", "1")
                        .param("name", "Report")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reports"));
        ArgumentCaptor<Report> argument = ArgumentCaptor.forClass(Report.class);
        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
        assertThat(argument.getValue().getName()).isEqualTo("Report");
    }
}