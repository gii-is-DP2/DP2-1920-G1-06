package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Room;

public interface RoomRepository {

	void save(@Valid Room room) throws DataAccessException;

	Room findRoomById(int roomId);

	Collection<Room> findRoomByPropertyId(Integer id);
	
	

}
