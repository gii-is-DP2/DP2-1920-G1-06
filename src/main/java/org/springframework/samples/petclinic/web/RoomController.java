
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	private static final String	VIEWS_ROOMS_CREATE_OR_UPDATE_FORM	= "rooms/createOrUpdateRoomForm";

	private PropertyService		propertyService;

	private final RoomService	roomService;


	@Autowired
	public RoomController(final RoomService roomService) {
		this.roomService = roomService;
	}

	//CREACION DE HABITACION ------------------------------------------------------------------------

	@GetMapping(value = "/rooms/new")
	public String initCreationForm(final Map<String, Object> model) {
		Room room = new Room();
		model.put("room", room);
		return RoomController.VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/rooms/new")
	public String processCreationForm(@Valid final Room room, final BindingResult result) {
		if (result.hasErrors()) {
			return RoomController.VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
		} else {
			this.roomService.saveRoom(room);
			return "redirect:/rooms/" + room.property.getId();
		}
	}

	//MODIFICACION DE HABITACION -----------------------------------------------------------------------

	@GetMapping(value = "/rooms/{roomId}/edit")
	public String initUpdateRoomForm(@PathVariable("roomId") final int roomId, final Model model) {
		Room room = this.roomService.findRoomById(roomId);
		model.addAttribute(room);
		return RoomController.VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/rooms/{roomId}/edit")
	public String processUpdateRoomForm(@Valid final Room room, final BindingResult result, @PathVariable("roomId") final int roomId) {
		if (result.hasErrors()) {
			return RoomController.VIEWS_ROOMS_CREATE_OR_UPDATE_FORM;
		} else {
			room.setId(roomId);
			this.roomService.saveRoom(room);
			return "redirect:/rooms/{roomId}";
		}
	}

}
