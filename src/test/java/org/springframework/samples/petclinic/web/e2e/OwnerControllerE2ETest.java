
package org.springframework.samples.petclinic.web.e2e;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class OwnerControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;
	


	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}
	
	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/new")
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.with(csrf())
							.param("dni", "12345678L")
							.param("birthDate", "1980-02-03")
							.param("gender", "0")
							.param("email","joe@gmail.com")
							.param("telephone", "0131676163"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/findOwners"));
	}
	
	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", 1)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("dni", "12345678P")
							.param("birthDate","2015-10-07")
							.param("gender", "0")
							.param("email", "email@email.es")
							.param("telephone", "608551023"))
				.andExpect(status().is2xxSuccessful());
	}
	@WithMockUser(username = "owner1", authorities = {"owner"})
	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}", 1)).andExpect(status().isOk())
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("owner", hasProperty("dni", is("0808129-D"))))
				.andExpect(model().attribute("owner", hasProperty("birthDate", is(LocalDate.of(2015, 10, 07)))))
				.andExpect(model().attribute("owner", hasProperty("gender", is(0))))
				.andExpect(model().attribute("owner", hasProperty("email", is("email@email.es"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("6085551023"))))
				.andExpect(view().name("owners/ownerDetails"));
	}
	
}
