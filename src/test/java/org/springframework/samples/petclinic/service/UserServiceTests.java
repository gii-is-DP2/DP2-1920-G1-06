package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserServiceTests {
	
	@Autowired
	protected UserService userService;

	private final static  String username = "owner1";
	
	@Test
	void shouldFindAUserWithCorrectUsername() {
		User user = this.userService.findByUsername(username);
		
		assertThat(user.getUsername()).isEqualTo("owner1");
		assertThat(user.getPassword()).isEqualTo("1");
	}
	
	@Test
	void shouldSaveAUser() {
		Collection<User> usersBeforeAdd = this.userService.findAll();
		Integer sizeOfUsersBeforeAdd = usersBeforeAdd.size();
		
		User user = new User();
		user.setUsername("user");
		user.setPassword("1234");		
		
		
		this.userService.saveUser(user);
		
		Collection<User> usersAfterAdd =  this.userService.findAll();
		Integer sizeOfUsersAfterAdd = usersAfterAdd.size();
		
		assertThat(sizeOfUsersAfterAdd).isEqualTo(sizeOfUsersBeforeAdd+1);
	}

}
