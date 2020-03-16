package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertyService {

	
	private PropertyRepository propertyRepository;	
	
	@Autowired
	public PropertyService(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}	
	
	@Transactional(readOnly = true)
	public Collection<Property> findAll() throws DataAccessException {
		return propertyRepository.findAll();
	}
}
