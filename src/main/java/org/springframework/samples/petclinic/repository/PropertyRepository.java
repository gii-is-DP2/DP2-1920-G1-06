package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import org.springframework.samples.petclinic.model.Property;

public interface PropertyRepository {

	Collection<Property> findAll() throws DataAccessException;

}
