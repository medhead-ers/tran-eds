package com.medhead.ers.tran_eds.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
class WelcomeControllerTest {

	@Autowired
	public MockMvc mockMvc;

	@Test
	void test_welcomeApp() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
	}
}
