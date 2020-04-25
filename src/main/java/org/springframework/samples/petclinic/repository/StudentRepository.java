package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Student;

public interface StudentRepository {

	
	Collection<Student> findByLastName(String lastName) throws DataAccessException;

	
	Student findById(int id) throws DataAccessException;
	
	void save(Student student) throws DataAccessException;


	Collection<Student> findAll() throws DataAccessException;


	Student findByUsername(String username);


}
