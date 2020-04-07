package org.springframework.samples.petclinic.service;


import static org.assertj.core.api.Assertions.assertThat;


import java.security.Provider.Service;
import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

class OwnerServiceTests {     
@Autowired
	protected OwnerService ownerService;

	private static final int TEST_OWNER_ID = 1;
 
	@Test
	void shouldSaveAOwner() {
		Collection<Owner> ownersBeforeAdd = this.ownerService.findAll();
		Integer sizeOfOwnersBeforeAdd = ownersBeforeAdd.size();
		
		Owner owner = new Owner();
		owner.setId(TEST_OWNER_ID);
		owner.setFirstName("Lucy");
		owner.setLastName("Franklin");
		owner.setDni("12345678X");
		owner.setBirthDate(LocalDate.of(1970, 11, 14));
		owner.setGender(0);
		owner.setEmail("lucy@gmail.com");
		owner.setTelephone("6085551024");
		
		this.ownerService.saveOwner(owner);
		
		Collection<Owner> ownersAfterAdd =  this.ownerService.findAll();
		Integer sizeOfOwnersAfterAdd = ownersAfterAdd.size();
		
		assertThat(sizeOfOwnersAfterAdd).isEqualTo(sizeOfOwnersBeforeAdd+1);
	}
}
