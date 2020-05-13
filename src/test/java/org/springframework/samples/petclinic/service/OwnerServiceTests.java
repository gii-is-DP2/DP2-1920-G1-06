package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class OwnerServiceTests {
	
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	protected UserService userService;
	
	
	@Test
	@Transactional
	public void shouldSaveOwner() {
		Collection<Owner> owners = this.ownerService.findAll();
		int ownersBeforeAdding = owners.size();
		
		Owner owner = new Owner();
		owner.setFirstName("Maria");
		owner.setLastName("Sanchez");
		owner.setDni("30261112C");
		owner.setBirthDate(LocalDate.of(1980, 4, 12));
		owner.setGender(1);
		owner.setEmail("mari2@gmail.com");
		owner.setTelephone("600222888");
		
		User user = new User();
		user.setUsername("mari");
		user.setPassword("marip");
		user.setEnabled(true);
		owner.setUser(user);
		
		
		this.ownerService.saveOwner(owner);
		assertThat(owner.getId().longValue()).isNotEqualTo(0);
		
		owners = this.ownerService.findAll();
		assertThat(owners.size()).isEqualTo(ownersBeforeAdding + 1);
	}

}
