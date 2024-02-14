package com.api.notes.controllers;

import com.api.notes.records.note.CreateNoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@WebAppConfiguration
class NoteControllerTest {
    private final static String BASE_URL = "/notes";
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void create_note () throws Exception {
        CreateNoteDTO createNoteDTO = new CreateNoteDTO("test three", "test test test test", 3L);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJSON(createNoteDTO)))
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }
    @Test
    void get_all_notes() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .queryParam("offset","0")
                .queryParam("limit","2")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, result.getResponse().getStatus());

        ObjectMapper objectMapper = new ObjectMapper();
        Object responseObject = objectMapper.readValue(result.getResponse().getContentAsString(), Object.class);
        assertTrue(responseObject instanceof List);
    }

    private String mapToJSON(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
