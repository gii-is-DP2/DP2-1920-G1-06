package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.repository.PersonRepository;

public interface SpringDataPersonRepository extends PersonRepository, Repository<Person, Integer> {

	@Override
	@Query("SELECT person FROM Person person  WHERE person.lastName LIKE :lastName%")
	public Collection<Person> findByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT person FROM Person person WHERE person.id =:id")
	public Person findById(@Param("id") int id);
}
