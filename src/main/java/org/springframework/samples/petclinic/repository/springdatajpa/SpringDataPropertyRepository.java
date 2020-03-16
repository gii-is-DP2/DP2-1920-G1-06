package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.repository.PropertyRepository;

public interface SpringDataPropertyRepository extends PropertyRepository, Repository<Property, Integer> {

}
