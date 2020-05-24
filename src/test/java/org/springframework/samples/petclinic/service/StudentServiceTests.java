package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class StudentServiceTests {
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected UserService userService;
	
	
	@Test
	@Transactional
	public void shouldSaveStudent() {
		Collection<Student> students = this.studentService.findAll();
		int studentsBeforeAdding = students.size();
		
		Student student = new Student();
		student.setFirstName("Carla");
		student.setLastName("Franflin");
		student.setDni("30263112C");
		student.setBirthDate(LocalDate.of(1985, 4, 12));
		student.setGender(1);
		student.setEmail("mari2@gmail.com");
		student.setTelephone("600122888");
		
		User user = new User();
		user.setUsername("mari2");
		user.setPassword("marip2");
		user.setEnabled(true);
		student.setUser(user);
		
		
		this.studentService.saveStudent(student);
		assertThat(student.getId().longValue()).isNotEqualTo(0);
		
		students = this.studentService.findAll();
		assertThat(students.size()).isEqualTo(studentsBeforeAdding + 1);
	}

}
