package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoomController {
	
	private static final String VIEWS_ROOMS_CREATE_OR_UPDATE_FORM = "rooms/createOrUpdateRoomForm";
	
	private PropertyService propertyService;
	
	private final RoomService roomService;
	
	@Autowired
	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}
	
	//CREACION DE HABITACION ------------------------------------------------------------------------

	@GetMapping(value = "/rooms/{propertyId}/new")
	public String initCreationForm(@PathVariable("propertyId") int propertyId, Map<String, Object> model) {
		Room room = new Room();
		model.put("room", room);
		Property property = propertyService.findPropertyById(propertyId);
		room.setProperty(property);
		return VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/rooms/{propertyId}/new")
	public String processCreationForm(@Valid Room room, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
		}
		else {			
			this.roomService.saveRoom(room);			
			return "redirect:/rooms/" + room.property.getId();
		}
	}
	
	//MODIFICACION DE HABITACION -----------------------------------------------------------------------
	
	@GetMapping(value = "/rooms/{roomId}/edit")
	public String initUpdateRoomForm(@PathVariable("roomId") int roomId, Model model) {
		Room room = this.roomService.findRoomById(roomId);
		model.addAttribute(room);
		return VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/rooms/{roomId}/edit")
	public String processUpdateRoomForm(@Valid Room room, BindingResult result,
			@PathVariable("roomId") int roomId) {
		if (result.hasErrors()) {
			return VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
		}
		else {
			room.setId(roomId);
			this.roomService.saveRoom(room);
			return "redirect:/rooms/{roomId}";
		}
	}
	
}
