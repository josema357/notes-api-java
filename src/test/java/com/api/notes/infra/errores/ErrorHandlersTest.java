package com.api.notes.infra.errores;

import com.api.notes.records.note.CreateNoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@WebAppConfiguration
class ErrorHandlersTest {

    private final static String BASE_URL= "/tags";
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void errorHandler404() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/2")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }
    @Test
    void errorHandler400() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }
    @Test
    void testErrorHandler500() throws Exception {
        CreateNoteDTO createNoteDTO = new CreateNoteDTO("test delete this", "test test test test", 5L);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJSON(createNoteDTO))).andReturn();
        assertEquals(500, result.getResponse().getStatus());

        JsonNode responseJSON = parseJSONResponse(result);
        assertTrue(responseJSON.has("message"));

    }

    private String mapToJSON(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
    private JsonNode parseJSONResponse(MvcResult result) throws IOException {
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(responseBody);
    }
}