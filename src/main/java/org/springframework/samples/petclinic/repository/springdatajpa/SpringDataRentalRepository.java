package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.RentalRepository;
import org.springframework.samples.petclinic.repository.RoomRepository;

public interface SpringDataRentalRepository extends RentalRepository, Repository<Rental, Integer>{

}
