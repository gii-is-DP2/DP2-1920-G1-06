package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.petclinic.model.Property;
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

	@CacheEvict(cacheNames="roomByPropertyId", allEntries=true)
	public void saveRoom(@Valid Room room) {

		roomRepository.save(room);
	}

	public Room findRoomById(int roomId) {

		return roomRepository.findRoomById(roomId);
	}

	@Cacheable("roomByPropertyId")
	public Collection<Room> findRoomByPropertyId(Integer id) {

		return roomRepository.findRoomByPropertyId(id);
	}

	public void assignRoom(Property property, Room room) {

		property.addRoom(room);
	
	}
}
