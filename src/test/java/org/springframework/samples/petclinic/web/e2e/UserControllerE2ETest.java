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

public class UserControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;

	// OWNER
	// ----------------------------------------------------------------------------------------------

	@WithMockUser(username = "owner1", authorities = { "owner" })
	@Test
	void testInitOwnerCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/new/owner"))
		.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
				.andExpect(MockMvcResultMatchers.view().name("users/createOwnerForm"));
	}

	@WithMockUser(username = "owner1", authorities = { "owner" })
	@Test
	void testProcessOwnerCreationFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/users/new/owner").with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("firstName", "George")
				.param("lastName", "Franklin").with(csrf())
				.param("dni", "12345678N")
				.param("birthDate", "1970-11-14")
				.param("gender", "0")
				.param("email", "george@gmail.com")
				.param("telephone", "608555023"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "owner1", authorities = { "owner" })
	@Test
	void testProcessOwnerCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/users/new/owner").with(csrf())
				.param("firstName", "George")
				.param("lastName", "Franklin")
				.param("dni", "12345678N")
				.param("birthDate", "1970-11-14")
				.param("gender", "14")
				.param("email", "george@gmail.com")
				.param("telephone", "608555023"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "gender"))
				.andExpect(view().name("users/createOwnerForm"));
	}

	// STUDENT
	// -------------------------------------------------------------------------------------------------------

	@WithMockUser(username = "student1", authorities = { "student" })
	@Test
	void testInitStudentCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/new/student"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("student"))
		.andExpect(MockMvcResultMatchers.view().name("users/createStudentForm"));
	}

	@WithMockUser(username = "student1", authorities = { "student" })
	@Test
	void testProcessStudentCreationFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/users/new/student").with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("firstName", "Lucy")
				.param("lastName", "Franklin").with(csrf())
				.param("dni", "12345678X")
				.param("birthDate", "1970-11-14")
				.param("email", "lucy@gmail.com")
				.param("gender", "1")
				.param("telephone", "6085551024"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "student1", authorities = { "student" })
	@Test
	void testProcessStudentCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/users/new/student").with(csrf())
				.param("firstName", "Lucy")
				.param("lastName", "Franklin")
				.param("dni", "1111111111")
				.param("birthDate", "1970-11-14")
				.param("email", "uuuuuuuuuuuuuuuuuu")
				.param("telephone", "2222222222222").param("gender", "7")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("student"))
				.andExpect(model().attributeHasFieldErrors("student", "dni"))
				.andExpect(model().attributeHasFieldErrors("student", "email"))
				.andExpect(model().attributeHasFieldErrors("student", "telephone"))
				.andExpect(model().attributeHasFieldErrors("student", "gender"))			
				.andExpect(view().name("users/createStudentForm"));
	}

}
