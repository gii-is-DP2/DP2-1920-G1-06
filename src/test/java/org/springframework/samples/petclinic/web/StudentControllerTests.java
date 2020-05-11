package org.springframework.samples.petclinic.web;
//



// OTRO EJEMPLITO DE TEST USALO SABIAMENTE




import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

///**
// * Test class for {@link studentController}
// *
// * @author Colin But
// */

@WebMvcTest(controllers=StudentController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class StudentControllerTests {

	private static final int TEST_STUDENT_ID = 1;

	@Autowired
	private StudentController studentController;

	@MockBean
	private StudentService studentService;
       
        @MockBean
	private UserService userService;
        
        @MockBean
        private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private Student lucy;

	@BeforeEach
	void setup() {

		lucy = new Student();
		lucy.setId(TEST_STUDENT_ID);
		lucy.setFirstName("Lucy");
		lucy.setLastName("Franklin");
		lucy.setDni("12345678X");
		lucy.setBirthDate(LocalDate.of(1970, 11, 14));
		lucy.setGender(1);
		lucy.setEmail("lucy@gmail.com");
		lucy.setTelephone("6085551024");
		
		given(this.studentService.findStudentById(TEST_STUDENT_ID)).willReturn(lucy);

	}

	@WithMockUser(value = "spring")
        @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/students/new")).andExpect(status().isOk()).andExpect(model().attributeExists("student"))
				.andExpect(view().name("students/createOrUpdateStudentForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/students/new").param("firstName", "Lucia").param("lastName", "Francisca")
							.with(csrf())
							.param("dni", "12345678X")
							.param("birthDate", "1970-11-14")
							.param("email","lucy@gmail.com")
							.param("gender", "1")
							.param("telephone", "6085551024"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/students/new")
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("dni", "1111111111")
							.param("birthDate", "1970-02-03")
							.param("email", "uuuuuuuuuuuuuuuuuu")
							.param("telephone", "2222222222222")
							.param("gender", "7"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("student"))
				.andExpect(model().attributeHasFieldErrors("student", "dni"))
				.andExpect(model().attributeHasFieldErrors("student", "email"))
				.andExpect(model().attributeHasFieldErrors("student", "telephone"))
				.andExpect(model().attributeHasFieldErrors("student", "gender"))
				.andExpect(view().name("students/createOrUpdateStudentForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/students/find")).andExpect(status().isOk()).andExpect(model().attributeExists("students"))
				.andExpect(view().name("students/findStudents"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessFindFormSuccess() throws Exception {
		given(this.studentService.findStudentByLastName("")).willReturn(Lists.newArrayList(lucy, new Student()));

		mockMvc.perform(get("/students")).andExpect(status().isOk()).andExpect(view().name("students/studentsList"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessFindFormByLastName() throws Exception {
		given(this.studentService.findStudentByLastName(lucy.getLastName())).willReturn(Lists.newArrayList(lucy));

		mockMvc.perform(get("/students").param("lastName", "Franklin")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/students/" + TEST_STUDENT_ID));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoStudentsFound() throws Exception {
		mockMvc.perform(get("/students").param("lastName", "Unknown Surname")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("student", "lastName"))
				.andExpect(model().attributeHasFieldErrorCode("student", "lastName", "notFound"))
				.andExpect(view().name("students/findStudents"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testInitUpdateStudentForm() throws Exception {
		mockMvc.perform(get("/students/{studentId}/edit", TEST_STUDENT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("student"))
				.andExpect(model().attribute("student", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("student", hasProperty("firstName", is("Lucy"))))
				.andExpect(model().attribute("student", hasProperty("dni", is("12345678X"))))
				.andExpect(model().attribute("student", hasProperty("gender", is(1))))
				.andExpect(model().attribute("student", hasProperty("birthDate", is(LocalDate.of(1970, 11, 14)))))
				.andExpect(model().attribute("student", hasProperty("email", is("lucy@gmail.com"))))
				.andExpect(model().attribute("student", hasProperty("telephone", is("6085551024"))))
				.andExpect(view().name("students/createOrUpdateStudentForm"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStudentFormSuccess() throws Exception {
		mockMvc.perform(post("/students/{studentId}/edit", TEST_STUDENT_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("dni", "12345678P")
							.param("birthDate", "1970-02-03")
							.param("gender", "1")
							.param("email", "joe2@gmail.com")
							.param("telephone", "01616291589"))
				.andExpect(status().is2xxSuccessful());
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateStudentFormHasErrors() throws Exception {
		mockMvc.perform(post("/students/{studentId}/edit", TEST_STUDENT_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("dni", "12345678P")
							.param("birthDate", "1970-02-03")
							.param("gender", "0")
							.param("email", "joe2@gmail.com")
							.param("telephone", "016162915111189"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("student"))
				.andExpect(model().attributeHasFieldErrors("student", "telephone"))
				.andExpect(view().name("students/createOrUpdateStudentForm"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testShowStudent() throws Exception {
		mockMvc.perform(get("/students/{studentId}", TEST_STUDENT_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("student", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("student", hasProperty("firstName", is("Lucy"))))
				.andExpect(model().attribute("student", hasProperty("dni", is("12345678X"))))
				.andExpect(model().attribute("student", hasProperty("birthDate", is(LocalDate.of(1970, 11, 14)))))
				.andExpect(model().attribute("student", hasProperty("gender", is(1))))
				.andExpect(model().attribute("student", hasProperty("email", is("lucy@gmail.com"))))
				.andExpect(model().attribute("student", hasProperty("telephone", is("6085551024"))))
				.andExpect(view().name("students/studentDetails"));
	}

}

