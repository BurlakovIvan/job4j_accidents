package ru.job4j.accident.controller;

import static org.mockito.Mockito.when;
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
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.service.AccidentService;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService service;

    @Test
    @WithMockUser
    public void testCreateAccident() throws Exception {
        this.mockMvc.perform(get("/addAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void testFormUpdateFail() throws Exception {
        this.mockMvc.perform(get("/formUpdateAccident")
                        .param("id", "-1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    @WithMockUser
    public void testFormCreate() throws Exception {
        when(service.findByAccidentTypeId(1))
                .thenReturn(Optional.of(new AccidentType(1, "AccidentType")));
        this.mockMvc.perform(post("/saveAccident")
                        .param("id", "1")
                        .param("name", "Accident")
                        .param("text", "Description")
                        .param("address", "Address")
                        .param("typeId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(service).create(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
        assertThat(argument.getValue().getName()).isEqualTo("Accident");
        assertThat(argument.getValue().getAddress()).isEqualTo("Address");
    }

    @Test
    @WithMockUser
    public void testFormUpdate() throws Exception {
        when(service.findByAccidentTypeId(1))
                .thenReturn(Optional.of(new AccidentType(1, "AccidentType")));
        service.create(new Accident(1, "null", "null", "null",
                service.findByAccidentTypeId(1).get(), null));
        this.mockMvc.perform(post("/updateAccident")
                        .param("id", "1")
                        .param("name", "Accident")
                        .param("text", "Description")
                        .param("address", "Address")
                        .param("typeId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(service).update(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
        assertThat(argument.getValue().getName()).isEqualTo("Accident");
        assertThat(argument.getValue().getAddress()).isEqualTo("Address");
    }
}