
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */

@Service
public class RentalService {

	private RentalRepository rentalRepository;

	@Autowired
	public RentalService(final RentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
	}

	@Transactional
	public void saveRental(final Rental rental) throws DataAccessException {

		this.rentalRepository.save(rental);

	}

	public Collection<Rental> findAll() {
		return this.rentalRepository.findAll();
	}

	public Collection<Rental> findRentalByOwnerUsername(String username) {
		return this.rentalRepository.findRentalByOwnerUsername(username);
	}

	public Collection<Rental> findRentalByStudentUsername(String username) {
		return this.rentalRepository.findRentalByStudentUsername(username);
	}

	public Rental findRentalById(int rentalId) {
		return this.rentalRepository.findRentalById(rentalId);
	}

}
