package org.springframework.samples.petclinic.web;
//



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

// OTRO EJEMPLITO DE TEST USALO SABIAMENTE




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

///**
// * Test class for {@link OwnerController}
// *
// * @author Colin But
// */

@WebMvcTest(controllers=OwnerController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class OwnerControllerTests {
	
	@Autowired
	private OwnerController ownerController;

	private static final int TEST_OWNER_ID = 1;

	
	@MockBean
	private OwnerService ownerService;
       
        @MockBean
	private UserService userService;
        
        @MockBean
        private AuthoritiesService authoritiesService; 
        

	@Autowired
	private MockMvc mockMvc;

	private Owner george;
	
	private User georgeUser;
	
	

	@BeforeEach
	void setup() {
		
		georgeUser = new User();
		georgeUser.setEnabled(true);
		georgeUser.setUsername("georgeUser");
		georgeUser.setPassword("georgePassword");
		
	
		george = new Owner();
		george.setId(TEST_OWNER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setDni("12345678N");
		george.setBirthDate(LocalDate.of(1970, 11, 14));
		george.setGender(0);
		george.setEmail("george@gmail.com");
		george.setTelephone("608555023");
		george.setUser(georgeUser);
		
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(george);

	}

	@WithMockUser(value = "spring")
        @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs")
							.with(csrf())
							.param("dni", "12345678L")
							.param("birthDate", "1980-02-03")
							.param("gender", "0")
							.param("email","joe@gmail.com")
							.param("telephone", "0131676163"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/new")
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("dni", "12345678")
							.param("birthDate", "1980-02-03")
							.param("gender", "14")
							.param("email","joe@gmail.com")
							.param("telephone", "0131676163"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "gender"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/findOwners"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessFindFormSuccess() throws Exception {
		given(this.ownerService.findOwnerByLastName("")).willReturn(Lists.newArrayList(george, new Owner()));

		mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessFindFormByLastName() throws Exception {
		given(this.ownerService.findOwnerByLastName(george.getLastName())).willReturn(Lists.newArrayList(george));

		mockMvc.perform(get("/owners").param("lastName", "Franklin")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoOwnersFound() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "Unknown Surname")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("owner", "lastName"))
				.andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
				.andExpect(view().name("owners/findOwners"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("owner"))
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("owner", hasProperty("birthDate", is(LocalDate.of(1970, 11, 14)))))
				.andExpect(model().attribute("owner", hasProperty("gender", is(0))))
				.andExpect(model().attribute("owner", hasProperty("email",is("george@gmail.com"))))
				.andExpect(model().attribute("owner", hasProperty("dni", is("12345678N"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("608555023"))))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("dni", "12345678P")
							.param("birthDate","1980-02-03")
							.param("gender", "0")
							.param("email", "joe2@gmail.com")
							.param("telephone", "016162919"))
				.andExpect(status().is3xxRedirection());
	}

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("dni", "12345678P")
							.param("birthDate","2024-02-08")
							.param("gender", "5" )
							.param("email", "joe2@gmail.com")
							.param("telephone","0161629159"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "birthDate"))
				.andExpect(model().attributeHasFieldErrors("owner", "gender"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("owner", hasProperty("dni", is("12345678N"))))
				.andExpect(model().attribute("owner", hasProperty("birthDate", is(LocalDate.of(1970, 11, 14)))))
				.andExpect(model().attribute("owner", hasProperty("gender", is(0))))
				.andExpect(model().attribute("owner", hasProperty("email", is("george@gmail.com"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("608555023"))))
				.andExpect(view().name("owners/ownerDetails"));
	}

}
