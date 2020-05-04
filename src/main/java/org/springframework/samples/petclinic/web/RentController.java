package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.samples.petclinic.service.RentalService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/properties/{propertyId}/rooms/{roomId}")
public class RentController {

	
		private static final String VIEWS_RENTAL_CREATE_FORM = "rentals/createRentalForm";

		private final OwnerService ownerService;
		
		private final RentalService rentalService;
		
		private final StudentService studentService;
		
		private final RoomService roomService;
		
		private final PropertyService propertyService;

		@Autowired
		public RentController(RentalService rentalService,OwnerService ownerService, StudentService studentService, RoomService roomService,PropertyService propertyService) {
			this.ownerService = ownerService;
			this.studentService = studentService;
			this.roomService = roomService;
			this.propertyService = propertyService;
			this.rentalService = rentalService;
		}
		@GetMapping(value = "/rental/new")
		public String initCreationForm(Map<String, Object> model) {
			Rental rental = new Rental();
			model.put("rental", rental);
			return VIEWS_RENTAL_CREATE_FORM;
		}

		@PostMapping(value = "/rental/new")
		public String processCreationForm(@PathVariable("roomId") final int roomId,@PathVariable("propertyId") final int propertyId,@Valid final Rental rental, final BindingResult result) {
			Room room = roomService.findRoomById(roomId);
			
			rental.setId(room.getId());
			rental.setPriceMonth(room.getPrice().doubleValue());
			rental.setIsAccepted(false);
			rental.setIsARequest(true);
			rental.setRoom(room);
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Student student = this.studentService.findStudentByUsername(username);
			rental.setStudent(student);
			
			this.rentalService.saveRental(rental);
			
			return "/welcome";
			
		}
		
		
		@GetMapping(value = "/rental/{rentalId}/edit")
		public String initUpdateForm(@PathVariable("propertyId") int propertyId, ModelMap model) {
			Rental rental = this.rentalService.findRentalById(propertyId);
			model.put("rental", rental);
			
			//Cambiar la referencia cuando esté el list
			return "VIEWS_RENTAL_CREATE_FORM";
		}
		
		@PostMapping(value = "/rental/{rentalId}/edit")
		public String processUpdateForm(@PathVariable("rentalId") int rentalId,@Valid Rental rental, BindingResult result) {
			if (result.hasErrors()) {
				//Cambiar la referencia cuando esté el list

				return VIEWS_RENTAL_CREATE_FORM;
			}
			else {
				Rental rentalD = this.rentalService.findRentalById(rentalId);
				
				LocalDate startDate = rental.getStartDate();
				LocalDate endDate = rental.getStartDate();
				Double priceMonth = rental.getPriceMonth();
				Room r = rental.getRoom();
				Student s = rental.getStudent();

				rentalD.setStartDate(startDate);
				rentalD.setEndDate(endDate);
				rentalD.setPriceMonth(priceMonth);
				rentalD.setIsARequest(false);
				rentalD.setRoom(r);
				rentalD.setStudent(s);
				
				//falta lo complicao
				
				
				
//				this.propertyService.saveProperty(propertyD);
				
				
				return "VIEWS_RENTAL_CREATE_FORM";
			}
		}


}
