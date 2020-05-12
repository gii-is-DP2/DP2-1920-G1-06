
package org.springframework.samples.petclinic.web.e2e;

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
public class StudentControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "student1", authorities = {
		"student"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("student"))
			.andExpect(MockMvcResultMatchers.view().name("students/createOrUpdateStudentForm"));
	}
	//TEST POSITIVOS------------------------------------------------------------------
	@WithMockUser(username = "student1", authorities = {
		"student"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/students/new").param("firstName", "Lucia").param("lastName", "Francisca").with(SecurityMockMvcRequestPostProcessors.csrf()).param("dni", "12345678X").param("birthDate", "1970-11-14")
			.param("email", "lucy@gmail.com").param("gender", "1").param("telephone", "6085551024")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "student1", authorities = {
		"student"
	})
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("students"))
			.andExpect(MockMvcResultMatchers.view().name("students/findStudents"));
	}

	@WithMockUser(username = "student1", authorities = {
		"student"
	})
	@Test
	void testInitUpdateStudentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/{studentId}/edit", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("student"))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("lastName", Matchers.is("Franklin")))).andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("firstName", Matchers.is("Paco"))))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("dni", Matchers.is("0808129-D")))).andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("email", Matchers.is("email@email.es"))))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("telephone", Matchers.is("6085551023")))).andExpect(MockMvcResultMatchers.view().name("students/createOrUpdateStudentForm"));
	}
	@WithMockUser(username = "student1", authorities = {
		"student"
	})
	@Test
	void testProcessUpdateStudentFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/students/{studentId}/edit", 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Paco").param("lastName", "Franklin").param("dni", "0808129-D").param("email", "email@email.es")
			.param("telephone", "6085551023")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	//TEST NEGATIVOS------------------------------------------------------------------

}
