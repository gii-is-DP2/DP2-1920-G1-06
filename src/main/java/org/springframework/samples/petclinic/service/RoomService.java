package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

private RoomRepository roomRepository;	
	
	@Autowired
	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public void saveRoom(@Valid Room room) {
		
		roomRepository.save(room);	
	}

	public Room findRoomById(int roomId) {
		
		return roomRepository.findRoomById(roomId);
	}	
	
}
