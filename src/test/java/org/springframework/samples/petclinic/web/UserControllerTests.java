package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)

public class UserControllerTests {

	@Autowired
	private UserController userController;

	@MockBean
	private UserService userService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private StudentService studentService;
	
	

	private Owner owner;
	private Student student;
	
	@MockBean
    private AuthoritiesService authoritiesService; 
  
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		///////////////////////////////
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("pass");
		user1.setEnabled(true);

		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setDni("12345678N");
		owner.setBirthDate(LocalDate.of(1970, 11, 14));
		owner.setGender(0);
		owner.setEmail("george@gmail.com");
		owner.setTelephone("608555023");

		owner.setUser(user1);

		given(this.userService.findByUsername("user1")).willReturn(user1);

		////////////////////////////////////////////////////////////////////////

		User user2 = new User();
		user2.setUsername("user2");
		user2.setPassword("pass");
		user2.setEnabled(true);

		student = new Student();
		student.setId(1);
		student.setFirstName("Lucy");
		student.setLastName("Franklin");
		student.setDni("12345678X");
		student.setBirthDate(LocalDate.of(1970, 11, 14));
		student.setGender(1);
		student.setEmail("lucy@gmail.com");
		student.setTelephone("6085551024");

		student.setUser(user2);

		given(this.userService.findByUsername("user2")).willReturn(user2);

	}

	// OWNER//////////////////////////////////////////////////////////////

	@WithMockUser(value = "spring")
	@Test
	void testInitOwnerCreationForm() throws Exception {
		mockMvc.perform(get("/users/new/owner"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("users/createOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessOwnerCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/users/new/owner").param("firstName", "George").param("lastName", "Franklin").with(csrf())
				.param("dni", "12345678N").param("birthDate", "1970-11-14").param("gender", "0")
				.param("email", "george@gmail.com").param("telephone", "608555023"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessOwnerCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/users/new/owner").with(csrf()).param("firstName", "George").param("lastName", "Franklin")
				.param("dni", "12345678N").param("birthDate", "1970-11-14").param("gender", "14")
				.param("email", "george@gmail.com").param("telephone", "608555023")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "gender"))
				.andExpect(view().name("users/createOwnerForm"));
	}

	// STUDENT/////////////////////////////////////////////////////////////////////////////////////////////////

	@WithMockUser(value = "spring")
	@Test
	void testInitStudentCreationForm() throws Exception {
		mockMvc.perform(get("/users/new/student")).andExpect(status().isOk()).andExpect(model().attributeExists("student"))
				.andExpect(view().name("users/createStudentForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessStudentCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/users/new/student")
				.param("firstName", "Lucy")
				.param("lastName", "Franklin").with(csrf())
				.param("dni", "12345678X")
				.param("birthDate", "1970-11-14")
				.param("email", "lucy@gmail.com")
				.param("gender", "1")
				.param("telephone", "6085551024"))
		.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessStudentCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/users/new/student").with(csrf())
				.param("firstName", "Lucy")
				.param("lastName", "Franklin")
				.param("dni", "1111111111")
				.param("birthDate", "1970-11-14")
				.param("email", "uuuuuuuuuuuuuuuuuu")
				.param("telephone", "2222222222222")
				.param("gender", "7")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("student"))
				.andExpect(model().attributeHasFieldErrors("student", "dni"))
				.andExpect(model().attributeHasFieldErrors("student", "email"))
				.andExpect(model().attributeHasFieldErrors("student", "telephone"))
				.andExpect(model().attributeHasFieldErrors("student", "gender"))
				.andExpect(view().name("users/createStudentForm"));
	}
}
