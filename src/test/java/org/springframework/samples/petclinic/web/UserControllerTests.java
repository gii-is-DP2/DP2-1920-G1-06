package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=UserController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class UserControllerTests {
	

	
	@Autowired
	private UserController userController;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("user1");
		user.setPassword("pass");
		user.setEnabled(true);

		
		given(this.userService.findByUsername("user1")).willReturn(user);

	}

	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/users/new")).andExpect(status().is3xxRedirection());
}
	
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/users/new").with(csrf())
				.param("username", "user2")
				.param("password", "pass2"))
				.andExpect(status().is3xxRedirection());
}
	//Aquí he puesto el mismo user de antes porque supuestamente no se puede repetir pero no lo se
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/users/new").with(csrf())
				.param("username", "user1")
				.param("password", "NOTIENERESTRICCIONNOSEQUEPONERAQUIPARAQUENOFUNCIONEOWOWWEHFEFIWQ"))
		
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("user"))
				.andExpect(model().attributeHasFieldErrors("user", "username"))
				.andExpect(view().name("users/register"));
	}
	
	
	
}
