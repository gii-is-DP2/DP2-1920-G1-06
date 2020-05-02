
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Rental;

public interface RentalRepository {

	void save(@Valid Rental rental);

	Collection<Rental> findAll();

	Collection<Rental> findRentalByOwnerUsername(String username);
	
	Collection<Rental> findRentalByStudentUsername(String username);

	Rental findRentalById(int rentalId);



}
