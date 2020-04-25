
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.samples.petclinic.service.RentalService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/properties/{propertyId}/rooms/{roomId}")
public class RentController {

	private static final String		VIEWS_RENTAL_CREATE_FORM	= "rentals/createRentalForm";

	private final OwnerService		ownerService;

	private final RentalService		rentalService;

	private final StudentService	studentService;

	private final RoomService		roomService;

	private final PropertyService	propertyService;


	@Autowired
	public RentController(final RentalService rentalService, final OwnerService ownerService, final StudentService studentService, final RoomService roomService, final PropertyService propertyService) {
		this.ownerService = ownerService;
		this.studentService = studentService;
		this.roomService = roomService;
		this.propertyService = propertyService;
		this.rentalService = rentalService;
	}
	@GetMapping(value = "/rental/new")
	public String initCreationForm(final Map<String, Object> model) {
		Rental rental = new Rental();
		model.put("rental", rental);
		return RentController.VIEWS_RENTAL_CREATE_FORM;
	}

	@PostMapping(value = "/rental/new")
	public String processCreationForm(@PathVariable("roomId") final int roomId, @PathVariable("propertyId") final int propertyId, @Valid final Rental rental, final BindingResult result) {
		Room room = this.roomService.findRoomById(roomId);

		rental.setId(room.getId());
		System.out.println(room.getPrice().doubleValue());
		rental.setPriceMonth(room.getPrice().doubleValue());
		rental.setIsAccepted(false);
		rental.setIsARequest(true);
		rental.setRoom(room);

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(username);
		Student student = this.studentService.findStudentByUsername(username);
		System.out.println(student);
		rental.setStudent(student);

		this.rentalService.saveRental(rental);

		return "/welcome";

	}

	@GetMapping(value = "/rentals")
	public String processFindForm(@PathVariable("userId") final int userId, final BindingResult result, final Map<String, Object> model) {

		Collection<Rental> results = this.rentalService.findRentalByOwnerId(userId);

		results.stream().filter(x -> x.getEndDate().isAfter(LocalDate.now()));

		model.put("selections", results);
		return "rentals/rentalsList";

	}

}
