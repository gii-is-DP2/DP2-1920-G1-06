package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RentalServiceTests {
	
	@Autowired
	protected RentalService			rentalService;

	private final static Integer	TEST_PROPERTY_ID	= 1;
	
	private final static String	TEST_STUDENT_USERNAME	= "student1";
	
	private final static String	TEST_OWNER_USERNAME	= "owner1";
	
	@Test
	void shouldFindARentalWithCorrectId() {
		Rental rental = this.rentalService.findRentalById(TEST_PROPERTY_ID);

		Assertions.assertThat(rental.getPriceMonth()).isEqualTo(20.0);
	}
	
	@Test
	void shouldFindARentalWithCorrectStudentUsername() {
		Collection<Rental> rentals = this.rentalService.findRentalByStudentUsername(TEST_STUDENT_USERNAME);

		Assertions.assertThat(rentals.stream().allMatch(x->x.getPriceMonth().equals(20.0))).isEqualTo(true);
	}
	
	@Test
	void shouldFindARentalWithCorrectOwnerUsername() {
		Collection<Rental> rentals = this.rentalService.findRentalByOwnerUsername(TEST_OWNER_USERNAME);

		Assertions.assertThat(rentals.stream().allMatch(x->x.getPriceMonth().equals(20.0))).isEqualTo(true);
	}

}
