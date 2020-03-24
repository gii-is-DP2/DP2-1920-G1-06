package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
	
	private StudentRepository studentRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}	

	@Transactional(readOnly = true)
	public Student findStudentById(int id) throws DataAccessException {
		return studentRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Student> findStudentByLastName(String lastName) throws DataAccessException {
		return studentRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveStudent(Student student) throws DataAccessException {
		//creating owner
		studentRepository.save(student);		
		//creating user
		userService.saveUser(student.getUser(),student.getId());
		//creating authorities
		authoritiesService.saveAuthorities(student.getUser().getUsername(), "student");
	}		

}
