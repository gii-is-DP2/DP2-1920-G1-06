
package org.springframework.samples.petclinic.web.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional

public class PropertyControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/properties/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("property"))
			.andExpect(MockMvcResultMatchers.view().name("properties/createOrUpdatePropertyForm"));
	}
	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/properties/new").with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("address", "Calle de las palmeras")
				.param("city", "London")
				.param("description", "Calle de las palmeras")
				.param("propertyType", "0")
				.param("surface","45")
				.param("totalRooms", "4"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	
	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testInitUpdatePropertyForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/properties/{propertyId}/edit", 1)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("property"))
				.andExpect(MockMvcResultMatchers.model().attribute("property", Matchers.hasProperty("address", Matchers.is("Base Militar de Rotas"))))
				.andExpect(MockMvcResultMatchers.model().attribute("property", Matchers.hasProperty("city", Matchers.is("Cadiz"))))
				.andExpect(MockMvcResultMatchers.model().attribute("property", Matchers.hasProperty("description", Matchers.is("Con muchos militares"))))
				.andExpect(MockMvcResultMatchers.model().attribute("property", Matchers.hasProperty("propertyType", Matchers.is(0))))
				.andExpect(MockMvcResultMatchers.model().attribute("property", Matchers.hasProperty("surface", Matchers.is(90))))
				.andExpect(MockMvcResultMatchers.model().attribute("property", Matchers.hasProperty("totalRooms", Matchers.is(4))))
				.andExpect(MockMvcResultMatchers.view().name("properties/createOrUpdatePropertyForm"));
	}
	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testProcessUpdatePropertyFormSuccess() throws Exception {
		mockMvc.perform(post("/properties/{propertyId}/edit", 1)
							.with(csrf())
							.param("address", "Calle de las palmeras")
							.param("city", "London")
							.param("description", "Calle de las palmeras")
							.param("propertyType", "0")
							.param("surface","45")
							.param("totalRooms", "4"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/properties/"+1+"/show"));
}
}
