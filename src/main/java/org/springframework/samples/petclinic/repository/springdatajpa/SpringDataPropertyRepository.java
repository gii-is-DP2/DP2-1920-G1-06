package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.repository.PropertyRepository;

public interface SpringDataPropertyRepository extends PropertyRepository, Repository<Property, Integer> {

	@Override
	@Query("SELECT p FROM Property p WHERE p.id =:id")
	public Property findPropertyById(@Param("id") int id);
}
