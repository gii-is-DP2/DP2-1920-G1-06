package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class StudentServiceTests {

	@Autowired
	protected StudentService studentService;

	private static final int TEST_STUDENT_ID = 1;
 
	
	
//	@Test
//	void shouldFindAStudentWithCorrectId() {
//		Student student = this.studentService.findStudentById(id);
//		
//		assertThat(user.getUsername()).isEqualTo("user1");
//		assertThat(user.getPassword()).isEqualTo("pass");
//	}
	
	@Test
	void shouldSaveAStudent() {
		Collection<Student> studentsBeforeAdd = this.studentService.findAll();
		Integer sizeOfStudentsBeforeAdd = studentsBeforeAdd.size();
		
		Student student = new Student();
		student.setId(TEST_STUDENT_ID);
		student.setFirstName("Lucy");
		student.setLastName("Franklin");
		student.setDni("12345678X");
		student.setBirthDate(LocalDate.of(1970, 11, 14));
		student.setGender(0);
		student.setEmail("lucy@gmail.com");
		student.setTelephone("6085551024");
		
		this.studentService.saveStudent(student);
		
		Collection<Student> studentsAfterAdd =  this.studentService.findAll();
		Integer sizeOfStudentsAfterAdd = studentsAfterAdd.size();
		
		assertThat(sizeOfStudentsAfterAdd).isEqualTo(sizeOfStudentsBeforeAdd+1);
	}
}
