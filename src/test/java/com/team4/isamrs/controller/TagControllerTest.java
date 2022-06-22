package com.team4.isamrs.controller;

import com.team4.isamrs.constants.AdvertiserConstants;
import com.team4.isamrs.constants.CustomerConstants;
import com.team4.isamrs.constants.TagConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagControllerTest {
    private static final String URL_PREFIX = "/tags";

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get(URL_PREFIX )).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Wi-Fi")));
    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + TagConstants.DB_ID)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(TagConstants.DB_ID))
                .andExpect(jsonPath("$.name").value(TagConstants.DB_NAME));
    }
}
