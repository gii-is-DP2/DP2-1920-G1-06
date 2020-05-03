
package org.springframework.samples.petclinic.web;
//

import java.time.LocalDate;

// OTRO EJEMPLITO DE TEST USALO SABIAMENTE

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/// **
// * Test class for {@link studentController}
// *
// * @author Colin But
// */

@WebMvcTest(controllers = StudentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class StudentControllerTests {

	private static final int	TEST_STUDENT_ID	= 1;

	@MockBean
	private StudentService		studentService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Student				lucy;


	@BeforeEach
	void setup() {

		this.lucy = new Student();
		this.lucy.setId(StudentControllerTests.TEST_STUDENT_ID);
		this.lucy.setFirstName("Lucy");
		this.lucy.setLastName("Franklin");
		this.lucy.setDni("12345678X");
		this.lucy.setBirthDate(LocalDate.of(1970, 11, 14));
		this.lucy.setGender(0);
		this.lucy.setEmail("lucy@gmail.com");
		this.lucy.setTelephone("6085551024");

		BDDMockito.given(this.studentService.findStudentById(StudentControllerTests.TEST_STUDENT_ID)).willReturn(this.lucy);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("student"))
			.andExpect(MockMvcResultMatchers.view().name("students/createOrUpdateStudentForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/students/new").param("firstName", "Joe").param("lastName", "Bloggs").with(SecurityMockMvcRequestPostProcessors.csrf()).param("dni", "12345678L").param("birthDate", "1980-02-03")
			.param("email", "joe@gmail.com").param("telephone", "0131676163")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/students/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("student")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("student", "dni")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("student", "email"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("student", "telephone")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("student", "gender"))
			.andExpect(MockMvcResultMatchers.view().name("students/createOrUpdateStudentForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("student"))
			.andExpect(MockMvcResultMatchers.view().name("students/findStudents"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {
		BDDMockito.given(this.studentService.findStudentByLastName("")).willReturn(Lists.newArrayList(this.lucy, new Student()));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/students")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("students/studentsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByLastName() throws Exception {
		BDDMockito.given(this.studentService.findStudentByLastName(this.lucy.getLastName())).willReturn(Lists.newArrayList(this.lucy));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/students").param("lastName", "Franklin")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/students/" + StudentControllerTests.TEST_STUDENT_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoStudentsFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students").param("lastName", "Unknown Surname")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("student", "lastName"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("student", "lastName", "notFound")).andExpect(MockMvcResultMatchers.view().name("students/findStudents"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateStudentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/{studentId}/edit", StudentControllerTests.TEST_STUDENT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("student"))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("lastName", Matchers.is("Franklin")))).andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("firstName", Matchers.is("lucy"))))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("dni", Matchers.is("12345678X")))).andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("email", Matchers.is("lucy2@gmail.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("telephone", Matchers.is("6085551024")))).andExpect(MockMvcResultMatchers.view().name("students/createOrUpdateStudentForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStudentFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/students/{studentId}/edit", StudentControllerTests.TEST_STUDENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("dni", "12345678P")
			.param("email", "joe2@gmail.com").param("telephone", "01616291589")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/students/{studentId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStudentFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/students/{studentId}/edit", StudentControllerTests.TEST_STUDENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("student")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("student", "address"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("student", "telephone")).andExpect(MockMvcResultMatchers.view().name("students/createOrUpdateStudentForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowStudent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/{studentId}", StudentControllerTests.TEST_STUDENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("lastName", Matchers.is("Franklin")))).andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("firstName", Matchers.is("lucy"))))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("dni", Matchers.is("12345678X")))).andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("birth_date", Matchers.is("1970-11-14"))))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("gender", Matchers.is("0")))).andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("email", Matchers.is("lucy@gmail.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("student", Matchers.hasProperty("telephone", Matchers.is("6085551024")))).andExpect(MockMvcResultMatchers.view().name("students/studentDetails"));
	}

}
