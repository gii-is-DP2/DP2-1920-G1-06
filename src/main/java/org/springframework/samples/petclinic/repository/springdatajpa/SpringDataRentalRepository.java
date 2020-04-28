package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.repository.RentalRepository;

public interface SpringDataRentalRepository extends RentalRepository, Repository<Rental, Integer>{

	@Override
	@Query("SELECT rental FROM Rental rental  WHERE rental.id =:id")
	public Rental findRentalById(@Param("id") int id);
	
}
