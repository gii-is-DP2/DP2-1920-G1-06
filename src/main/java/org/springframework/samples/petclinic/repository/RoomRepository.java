package org.springframework.samples.petclinic.repository;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Room;

public interface RoomRepository {

	void save(@Valid Room room);

	Room findRoomById(int roomId);

}
