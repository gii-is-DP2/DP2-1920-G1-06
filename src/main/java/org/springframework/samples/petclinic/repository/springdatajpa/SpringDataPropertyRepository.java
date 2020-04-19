package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.repository.PropertyRepository;

public interface SpringDataPropertyRepository extends PropertyRepository, Repository<Property, Integer> {

	@Override
	@Query("SELECT property FROM Property property  WHERE property.city LIKE :location%")
	public Collection<Property> findPropertyByLocation(@Param("location") String location);
}
