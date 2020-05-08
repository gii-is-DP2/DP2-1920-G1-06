package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.service.RentalService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



public class RentalValidator implements Validator{

	private final RentalService rentalService;
	private final String EMPTY = "Empty";
	private final String DESCRIPTION = "description";
	private final String NO_VALID_DATE = "NoValidInitDate";
	private final String INIT_DATE  = "initDate";
	private final String END_DATE  = "endDate";
		
	@Autowired
	public RentalValidator(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Rental.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Rental rental = (Rental) target;
		LocalDate fechaInicio = rental.getStartDate();
		LocalDate fechaFin = rental.getEndDate();
		
		notCollapsedDates(fechaInicio,fechaFin,errors);
		
		
	}
	private void notCollapsedDates(LocalDate nuevaFechaPrimera,LocalDate nuevaFechaSegunda,Errors errors){
		
		if (nuevaFechaPrimera.isAfter(nuevaFechaSegunda)) {
			errors.rejectValue(this.INIT_DATE, this.NO_VALID_DATE,
					"La fecha de llegada no puede ser después que la de salida");
		}
		if (nuevaFechaPrimera.isBefore(LocalDate.now())) {
			errors.rejectValue(this.INIT_DATE, this.NO_VALID_DATE,
					"La fecha de llegada no puede ser una fecha en pasado");
		}
		if (nuevaFechaSegunda.isBefore(LocalDate.now())) {
			errors.rejectValue(this.END_DATE, this.NO_VALID_DATE, 
					"La fecha de salida no puede ser una fecha en pasado");
		}
	}


	
}
