package org.springframework.samples.petclinic.repository.springdatajpa;


import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.repository.PersonRepository;

public interface SpringDataPersonRepository extends PersonRepository, Repository<Person, Integer> {


	
}
