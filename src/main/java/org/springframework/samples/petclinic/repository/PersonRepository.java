package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Person;

public interface PersonRepository {

	void save(Person person) throws DataAccessException;

}
