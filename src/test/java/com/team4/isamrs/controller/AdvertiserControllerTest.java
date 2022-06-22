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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertiserControllerTest {

    private static final String URL_PREFIX = "/advertisers";

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
    public void testGetAdvertiser() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + AdvertiserConstants.DB_ID)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(AdvertiserConstants.DB_ID))
                .andExpect(jsonPath("$.username").value(AdvertiserConstants.DB_USERNAME))
                .andExpect(jsonPath("$.firstName").value(AdvertiserConstants.DB_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(AdvertiserConstants.DB_LAST_NAME))
                .andExpect(jsonPath("$.city").value(AdvertiserConstants.DB_CITY))
                .andExpect(jsonPath("$.countryCode").value(AdvertiserConstants.DB_COUNTRY_CODE))
                .andExpect(jsonPath("$.address").value(AdvertiserConstants.DB_ADDRESS))
                .andExpect(jsonPath("$.phoneNumber").value(AdvertiserConstants.DB_PHONE_NUMBER));
    }

    @Test
    public void testGetAverageRating() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + AdvertiserConstants.DB_ID + "/reviews/average"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void testGetApprovedReviews() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + AdvertiserConstants.DB_ID + "/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].customer.firstName").value(hasItem(CustomerConstants.DB_FIRST_NAME)));
    }
}
