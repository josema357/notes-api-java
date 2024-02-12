package com.api.notes.controllers;

import com.api.notes.records.tag.CreateTagDTO;
import com.api.notes.records.tag.UpdateTagDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@WebAppConfiguration
class TagControllerTest{
    private final static String BASE_URL= "/tags";
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void create_tag() throws Exception {
        CreateTagDTO createTagDTO = new CreateTagDTO("Tag three");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJSON(createTagDTO)))
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }
    @Test
    void get_all_tags() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .queryParam("offset","0")
                .queryParam("limit", "2")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
    @Test
    void get_tag_by_id () throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
    @Test
    void update_tag() throws Exception {
        UpdateTagDTO updateTagDTO = new UpdateTagDTO("updated tag", false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL+"/3")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(mapToJSON(updateTagDTO)))
                .andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }
    @Test
    void delete_tag() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL+"/5")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(204, result.getResponse().getStatus());
    }


    private String mapToJSON(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}