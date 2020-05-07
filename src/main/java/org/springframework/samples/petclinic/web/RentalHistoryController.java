
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.RentalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RentalHistoryController {

	private final RentalService		rentalService;

	@Autowired
	public RentalHistoryController(final RentalService rentalService) {

		this.rentalService = rentalService;
	}
	
	
	
	
	
	
	@GetMapping(value = "/rentals/{rentalId}/accept")
	public String acceptRental(@PathVariable("rentalId") final int rentalId, final Model model) {
		
		Rental rental = this.rentalService.findRentalById(rentalId);
		
		rental.setIsAccepted(true);
		rental.setIsARequest(false);
		
		this.rentalService.saveRental(rental);
		
		return "welcome";
	}
	
	
	
	@GetMapping(value = "/rentals/{rentalId}/reject")
	public String rejectRental(@PathVariable("rentalId") final int rentalId,final Map<String, Object> model) {
		
		Rental rental = this.rentalService.findRentalById(rentalId);
		
		rental.setIsAccepted(false);
		rental.setIsARequest(false);

		this.rentalService.saveRental(rental);
		
		return "welcome";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//LISTADO DE PETICIONES
	
	@GetMapping(value = "/request")
	public String processFindRequestForm(final Rental rental, final BindingResult result, final Map<String, Object> model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		

		if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(x->x.toString()).anyMatch(x->x.equals("owner"))) {
			
			Collection<Rental> results = this.rentalService.findRentalByOwnerUsername(username);

			results = results.stream().filter(x -> x.getEndDate().isAfter(LocalDate.now())).filter(x->!x.getIsARequest()).collect(Collectors.toList());
			
			model.put("selections", results);
			
		}else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(x->x.toString()).anyMatch(x->x.equals("student"))) {
			
			Collection<Rental> results = this.rentalService.findRentalByStudentUsername(username);
			
			model.put("selections", results.stream().filter(x->x.getIsAccepted()).collect(Collectors.toList()));
		}

		return "rentals/rentalsList";

	}
	
	//MOSTRADO DE ALQUILER -----------------------------------------------------------------
	
	@GetMapping("/request/{rentalId}")
	public ModelAndView showRequest(@PathVariable("rentalId") final int rentalId) {
		ModelAndView mav = new ModelAndView("rentals/rentalDetails");
		mav.addObject(this.rentalService.findRentalById(rentalId));
		return mav;
	}
	

	
	//LISTADO DE ALQUILERES ------------------------------------------------------------------

	@GetMapping(value = "/rentals")
	public String processFindForm(final Rental rental, final BindingResult result, final Map<String, Object> model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		

		if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(x->x.toString()).anyMatch(x->x.equals("owner"))) {
			
			Collection<Rental> results = this.rentalService.findRentalByOwnerUsername(username);

			results = results.stream().filter(x -> x.getEndDate().isAfter(LocalDate.now())).filter(x->x.getIsAccepted()).collect(Collectors.toList());
			
			model.put("selections", results);
			
		}else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(x->x.toString()).anyMatch(x->x.equals("student"))) {
			
			Collection<Rental> results = this.rentalService.findRentalByStudentUsername(username);
			
			model.put("selections", results.stream().filter(x->x.getIsAccepted()).collect(Collectors.toList()));
		}

		return "rentals/rentalsList";

	}
	
	//MOSTRADO DE ALQUILER -----------------------------------------------------------------
	
	@GetMapping("/rentals/{rentalId}")
	public ModelAndView showRental(@PathVariable("rentalId") final int rentalId) {
		ModelAndView mav = new ModelAndView("rentals/rentalDetails");
		mav.addObject(this.rentalService.findRentalById(rentalId));
		return mav;
	}

}
