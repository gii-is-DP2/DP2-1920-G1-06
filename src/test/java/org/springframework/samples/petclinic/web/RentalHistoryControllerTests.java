package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.samples.petclinic.service.RentalService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RentalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class RentalHistoryControllerTests {
	
	private static final int TEST_RENTAL_ID = 1;

	private static final int TEST_PROPERTY_ID = 1;

	private static final int TEST_OWNER_ID = 1;
	
	private static final int TEST_ROOM_ID = 1;
	
	private static final int TEST_STUDENT_ID = 1;

	@Autowired
	private RentalController rentalController;

	@MockBean
	private RentalService rentalService;

	@MockBean
	private PropertyService propertyService;
	
	@MockBean
	private RoomService roomService;

	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private StudentService studentService;
	
	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		
		User lucyUser = new User();
		lucyUser.setEnabled(true);
		lucyUser.setUsername("lucyUser");
		lucyUser.setPassword("lucyPassword");
		
		Student lucy = new Student();
		lucy.setId(TEST_STUDENT_ID);
		lucy.setFirstName("Lucy");
		lucy.setLastName("Franklin");
		lucy.setDni("12345678X");
		lucy.setBirthDate(LocalDate.of(1970, 11, 14));
		lucy.setGender(1);
		lucy.setEmail("lucy@gmail.com");
		lucy.setTelephone("6085551024");
		
		User georgeUser = new User();
		georgeUser.setEnabled(true);
		georgeUser.setUsername("georgeUser");
		georgeUser.setPassword("georgePassword");
		
		Owner george = new Owner();
		george.setId(TEST_OWNER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setDni("12345678N");
		george.setBirthDate(LocalDate.of(1970, 11, 14));
		george.setGender(0);
		george.setEmail("george@gmail.com");
		george.setTelephone("608555023");
		george.setUser(georgeUser);
		
		Property property = new Property();
		property.setId(TEST_PROPERTY_ID);
		property.setAddress("Direccion");
		property.setCity("Ciudad");
		property.setDescription("Descripcion");
		property.setPropertyType(0);
		property.setSurface(45);
		property.setTotalRooms(3);
		property.setOwner(george);
		
		Room room = new Room();
		room.setId(TEST_ROOM_ID);
		room.setRoomNumber("3");
		room.setSurface(2000);
		room.setPrice(3.0);
		room.setExtWindow(0);
		room.setTamCloset(4);		
		room.setProperty(property);

		Rental rental = new Rental();
		rental.setId(TEST_RENTAL_ID);
		rental.setStartDate(LocalDate.of(2020, 03, 10));
		rental.setEndDate(LocalDate.of(2020, 03, 15));
		rental.setPriceMonth(50.);
		rental.setIsAccepted(false);
		rental.setIsARequest(true);
		rental.setOwner(george);
		rental.setStudent(lucy);
		rental.setRoom(room);

		given(this.rentalService.findRentalById(TEST_RENTAL_ID)).willReturn(rental);

	}
	
	

}
