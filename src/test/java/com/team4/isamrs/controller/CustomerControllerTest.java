package com.team4.isamrs.controller;

import com.team4.isamrs.constants.AdvertiserConstants;
import com.team4.isamrs.constants.CustomerConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {

    private static final String URL_PREFIX = "/customers";

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
    public void testFindById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + CustomerConstants.DB_ID)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(CustomerConstants.DB_ID))
                .andExpect(jsonPath("$.username").value(CustomerConstants.DB_USERNAME))
                .andExpect(jsonPath("$.firstName").value(CustomerConstants.DB_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(CustomerConstants.DB_LAST_NAME))
                .andExpect(jsonPath("$.city").value(CustomerConstants.DB_CITY))
                .andExpect(jsonPath("$.countryCode").value(CustomerConstants.DB_COUNTRY_CODE))
                .andExpect(jsonPath("$.address").value(CustomerConstants.DB_ADDRESS))
                .andExpect(jsonPath("$.phoneNumber").value(CustomerConstants.DB_PHONE_NUMBER));
    }

    @Test
    public void testGetReviews() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + CustomerConstants.DB_ID + "/reviews")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(5)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem("you've got mail")))
                .andExpect(jsonPath("$.[*].customer.firstName").value(hasItem(CustomerConstants.DB_FIRST_NAME)));
    }
}
