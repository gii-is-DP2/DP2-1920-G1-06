package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.model.User;

public interface RentalRepository {

	void save(@Valid Rental rental);

	Collection<Rental> findAll();

	Rental findRentalById(int id);
	
	
}
