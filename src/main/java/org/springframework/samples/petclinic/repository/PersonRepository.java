package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Person;

public interface PersonRepository {
	
	Person findById(int id) throws DataAccessException;
	
	Collection<Person> findByLastName(String lastname) throws DataAccessException;
	
	void save(Person person) throws DataAccessException;


}
