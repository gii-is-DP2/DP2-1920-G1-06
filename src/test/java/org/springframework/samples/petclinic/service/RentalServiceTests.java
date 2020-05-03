
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
	protected RentalService	rentalService;

	private final String	OWNER		= "owner1";
	private final String	STUDENT		= "student1";
	private final int		RENTAL_ID	= 1;


	@Test
	void shouldfindRentalByOwnerUsername() {
		Collection<Rental> rentals = this.rentalService.findRentalByOwnerUsername("owner1");
		System.out.println(rentals);
		Assertions.assertThat(rentals.stream().allMatch(x -> x.getOwner().getUser().getUsername().equals(this.OWNER))).isTrue();

	}

	@Test
	void shouldfindRentalByStudentUsername() {
		Collection<Rental> rentals = this.rentalService.findRentalByOwnerUsername(this.STUDENT);
		Assertions.assertThat(rentals.stream().allMatch(x -> x.getStudent().getUser().getUsername().equals(this.STUDENT))).isTrue();
	}

	@Test
	void shouldFindRentalById() {
		Rental rental = this.rentalService.findRentalById(this.RENTAL_ID);

		Assertions.assertThat(rental.getStartDate()).isEqualTo("2020-06-01");
	}

}
