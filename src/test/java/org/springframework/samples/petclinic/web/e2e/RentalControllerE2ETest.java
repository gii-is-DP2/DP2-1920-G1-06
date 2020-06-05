package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional

public class RentalControllerE2ETest {
	
	private final String TEST_PROPERTY_ID = "1";
	
	private final String TEST_ROOM_ID = "1";
	
	private final String TEST_RENTAL_ID = "1";
	
	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "student1", authorities = { "student" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/properties/{propertyId}/rooms/{roomId}/rental/new", TEST_PROPERTY_ID, TEST_ROOM_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("rental"))
			.andExpect(MockMvcResultMatchers.view().name("rentals/createRentalForm"));
	}

	@WithMockUser(username = "student1", authorities = { "student" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/properties/{propertyId}/rooms/{roomId}/rental/new", TEST_PROPERTY_ID, TEST_ROOM_ID).with(csrf())
			.param("startDate", "2020-10-10")
			.param("endDate","2020-10-15")
			.param("priceMonth", "50")
			.param("isAccepted", "false")
			.param("isARequest", "true"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "student1", authorities = { "student" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/properties/{propertyId}/rooms/{roomId}/rental/new", TEST_PROPERTY_ID, TEST_ROOM_ID).with(csrf())
			.param("startDate", "2020-10-10")
			.param("endDate","2020-10-15")
			.param("priceMonth", "hola")
			.param("isAccepted", "false")
			.param("isARequest", "true"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("rental"))
			.andExpect(model().attributeHasFieldErrors("rental", "priceMonth"))
			.andExpect(view().name("rentals/createRentalForm"));
	}


	@WithMockUser(username = "owner1", authorities = { "owner" })
	@Test
	void testProcessUpdatePropertyFormSuccess() throws Exception {
		mockMvc.perform(post("/properties/{propertyId}/rooms/{roomId}/rental/{rentalId}/edit", TEST_PROPERTY_ID, TEST_ROOM_ID, TEST_RENTAL_ID).with(csrf())
				.param("startDate", "2020-10-10")
				.param("endDate","2020-10-15")
				.param("priceMonth", "50")
				.param("isAccepted", "true")
				.param("isARequest", "false"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "owner1", authorities = { "owner" })
	@Test
	void testProcessUpdatePropertyFormHasErrors() throws Exception {
		mockMvc.perform(post("/properties/{propertyId}/rooms/{roomId}/rental/{rentalId}/edit", TEST_PROPERTY_ID, TEST_ROOM_ID, TEST_RENTAL_ID).with(csrf())
			.param("startDate", "2020-10-10")
			.param("endDate","2020-10-15")
			.param("priceMonth", "50")
			.param("isAccepted", "hola")
			.param("isARequest", "buenas"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("rental"))
			.andExpect(model().attributeHasFieldErrors("rental", "isAccepted"))
			.andExpect(model().attributeHasFieldErrors("rental", "isARequest"))
			.andExpect(view().name("rentals/createRentalForm"));
	}
	
	
}
