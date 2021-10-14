package com.karithrastarson.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.service.ArchitectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArchitectController.class)
public class ArchitectControllerTest {

    @MockBean
    ArchitectService architectService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void it_should_return_architect_id() throws Exception {
        ArchitectController.ArchitectItem requestBody = new ArchitectController.ArchitectItem("john", "13-01-1992", "DTU");

        Architect architect = new Architect();
        when(architectService.addArchitect(any(String.class), any(String.class), any(String.class))).thenReturn(architect);

        mockMvc.perform(MockMvcRequestBuilders.post("/architect")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Architect added with ID " + architect.getId()));
    }

}
