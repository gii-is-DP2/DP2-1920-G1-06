package org.springframework.samples.petclinic.service;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
	
	private PersonRepository personRepository;	

	@Autowired
	public void PersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}	

//	@Transactional(readOnly = true)
//	public Person findOwnerById(int id) throws DataAccessException {
//		return personRepository.findById(id);
//	}
//
//	@Transactional(readOnly = true)
//	public Collection<Person> findPersonByLastName(String lastName) throws DataAccessException {
//		return personRepository.findByLastName(lastName);
//	}

	@Transactional
	public void savePerson(Person person) throws DataAccessException {
		personRepository.save(person);		
		
	}	

}
