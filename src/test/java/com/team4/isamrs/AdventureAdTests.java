package com.team4.isamrs;

import com.team4.isamrs.controller.AdventureController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ComponentScan
@SpringBootTest
@AutoConfigureMockMvc
public class AdventureAdTests {

	@Autowired
	AdventureController adventureController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void submitEmpty() throws Exception {
		String payload = "{}";
		mockMvc.perform(MockMvcRequestBuilders.post("/ads/adventures")
				.content(payload)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void submitInvalidCountryCode() throws Exception {
		String payload = "{ \"address\": {\"countryCode\": \"AAA\"}}";
		assertTrue(mockMvc.perform(MockMvcRequestBuilders.post("/ads/adventures")
				.content(payload)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString()
				.contains("Field must be equal to ISO 3116 country code"));
	}

	@Test
	void submitValidCountryCode() throws Exception {
		String payload = "{ \"address\": {\"countryCode\": \"RS\"}}";
		assertFalse(mockMvc.perform(MockMvcRequestBuilders.post("/ads/adventures")
						.content(payload)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString()
				.contains("Field must be equal to ISO 3116 country code"));
	}

	@Test
	void submitValidCountryCodeLowercase() throws Exception {
		String payload = "{ \"address\": {\"countryCode\": \"rs\"}}";
		assertFalse(mockMvc.perform(MockMvcRequestBuilders.post("/ads/adventures")
						.content(payload)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString()
				.contains("Field must be equal to ISO 3116 country code"));
	}

	@Test
	void submitBlankCountryCode() throws Exception {
		String payload = "{ \"address\": {\"countryCode\": \"\"}}";
		assertTrue(mockMvc.perform(MockMvcRequestBuilders.post("/ads/adventures")
						.content(payload)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString()
				.contains("\"address.countryCode\":"));
	}

}
