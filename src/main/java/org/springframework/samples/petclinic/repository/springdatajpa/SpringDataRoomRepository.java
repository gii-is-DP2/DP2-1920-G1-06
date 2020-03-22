
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.RoomRepository;

public interface SpringDataRoomRepository extends RoomRepository, Repository<Room, Integer> {

	@Override
	@Query("SELECT r FROM Room r WHERE r.id =:id")
	Room findRoomById(@Param("id") int id);

}
