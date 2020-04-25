
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.repository.RentalRepository;

public interface SpringDataRentalRepository extends RentalRepository, Repository<Rental, Integer> {

	@Override
	@Query("SELECT r FROM Rental r WHERE r.room.property.owner.id =:ownerId")
	Collection<Rental> findRentalByOwnerId(Integer ownerId);

}
