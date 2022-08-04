package com.karithrastarson.registry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karithrastarson.registry.entity.Architect;
import com.karithrastarson.registry.exception.BadRequestException;
import com.karithrastarson.registry.exception.DuplicateException;
import com.karithrastarson.registry.exception.NoItemFoundException;
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
import static org.mockito.ArgumentMatchers.eq;
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
    public void it_should_return_architect_id_200() throws Exception {
        ArchitectController.ArchitectItem requestBody = new ArchitectController.ArchitectItem("john", "13-01-1992", "DTU");

        Architect architect = new Architect();
        when(architectService.addArchitect(any(String.class), any(String.class), any(String.class))).thenReturn(architect);

        mockMvc.perform(MockMvcRequestBuilders.post("/architect")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Architect added with ID " + architect.getId()));

        // University parameter can be empty
        requestBody = new ArchitectController.ArchitectItem("John jr.", "13-01-1992", "");

        architect = new Architect();
        when(architectService.addArchitect(eq("John jr."), eq("13-01-1992"), eq(""))).thenReturn(architect);

        mockMvc.perform(MockMvcRequestBuilders.post("/architect")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Architect added with ID " + architect.getId()));
    }

    @Test
    public void it_should_return_duplicate_error_409() throws Exception {
        ArchitectController.ArchitectItem requestBody = new ArchitectController.ArchitectItem("john", "13-01-1992", "DTU");
        when(architectService.addArchitect(eq("john"), eq("DTU"), eq("13-01-1992"))).thenThrow(new DuplicateException("john"));

        mockMvc.perform(MockMvcRequestBuilders.post("/architect")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("Item with identifier john already present in db"));
    }

    @Test
    public void it_should_return_bad_request_error_400() throws Exception {
        // When no name is provided, then bad request is returned
        ArchitectController.ArchitectItem requestBody = new ArchitectController.ArchitectItem("", "13-01-1992", "DTU");
        when(architectService.addArchitect(eq(""), eq("DTU"), eq("13-01-1992"))).thenThrow(new BadRequestException("name"));

        mockMvc.perform(MockMvcRequestBuilders.post("/architect")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Parameter \"name\" missing or malformed"));

        // When no date of birth is provided, then bad request is returned
        requestBody = new ArchitectController.ArchitectItem("John Doe", "", "DTU");
        when(architectService.addArchitect(eq("John Doe"), eq("DTU"), eq(""))).thenThrow(new BadRequestException("date of birth"));

        mockMvc.perform(MockMvcRequestBuilders.post("/architect")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Parameter \"date of birth\" missing or malformed"));
    }

    @Test
    public void it_should_return_architect_details() throws Exception {
        //When looking for architect ID that exist, the architect object is returned
        Architect architect = new Architect("John", "13-01-1992");
        String id = architect.getId().toString();
        when(architectService.getArchitectById(id)).thenReturn(architect);

        mockMvc.perform(MockMvcRequestBuilders.get("/architect/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"architectId\":0,\"fullName\":\"John\",\"dob\":\"13-01-1992\",\"university\":null,\"profilePicture\":0,\"buildings\":null,\"assets\":null,\"id\":0}"));
    }

    @Test
    public void it_should_return_404_not_found() throws Exception {
        String id = "0";
        when(architectService.getArchitectById(id)).thenThrow(new NoItemFoundException(id));

        mockMvc.perform(MockMvcRequestBuilders.get("/architect/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Item with ID " + id + " not found"));
    }

   @Test
   public void it_should_update_architect_return_200() throws Exception {
       Architect architect = new Architect("John", "13-01-1992");
       architect.setUniversity("KTH");
       String id = architect.getId().toString();
       when(architectService.updateArchitectInfo(id, Long.parseLong("123"), "KTH")).thenReturn(architect);

       ArchitectController.ArchitectUpdatedInfo requestBody = new ArchitectController.ArchitectUpdatedInfo("KTH", Long.parseLong("123"));
       mockMvc.perform(MockMvcRequestBuilders.put("/architect/" + id)
               .content(objectMapper.writeValueAsString(requestBody))
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().string("{\"architectId\":0,\"fullName\":\"John\",\"dob\":\"13-01-1992\",\"university\":\"KTH\",\"profilePicture\":123,\"buildings\":null,\"assets\":null,\"id\":0}"));
   }
}
