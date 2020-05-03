
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Rental;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RentalService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RentalHistoryController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class RentalHistoryControllerTests {

	private static final int		RENTAL_ID		= 1;

	@Autowired
	private RentalHistoryController	rentalHistoryController;

	@MockBean
	private RentalService			rentalService;

	@Autowired
	private MockMvc					mockMvc;

	//ROOM----------------------------------------
	private static final int		TEST_ROOM_ID	= 1;

	@MockBean
	private RoomService				roomService;

	//STUDENT------------------------------------

	private static final int		TEST_STUDENT_ID	= 1;

	@MockBean
	private StudentService			studentService;

	@MockBean
	private UserService				userService;

	@MockBean
	private AuthoritiesService		authoritiesService;

	//OWNER--------------------------------------

	private static final int		TEST_OWNER_ID	= 1;

	@MockBean
	private OwnerService			ownerService;

	//TEST----------------------------------------


	@BeforeEach
	void setup() {

		Room room = new Room();
		room.setId(RentalHistoryControllerTests.TEST_ROOM_ID);
		room.setRoomNumber("3");
		room.setSurface(2000);
		room.setPrice(3.0);
		room.setExtWindow(0);
		room.setTamCloset(4);

		BDDMockito.given(this.roomService.findRoomById(RentalHistoryControllerTests.TEST_ROOM_ID)).willReturn(room);

		Student student = new Student();
		student.setId(RentalHistoryControllerTests.TEST_STUDENT_ID);
		student.setFirstName("Lucy");
		student.setLastName("Franklin");
		student.setDni("12345678X");
		student.setBirthDate(LocalDate.of(1970, 11, 14));
		student.setGender(0);
		student.setEmail("lucy@gmail.com");
		student.setTelephone("6085551024");

		BDDMockito.given(this.studentService.findStudentById(RentalHistoryControllerTests.TEST_STUDENT_ID)).willReturn(student);

		Owner owner = new Owner();
		owner.setId(RentalHistoryControllerTests.TEST_OWNER_ID);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setDni("12345678N");
		owner.setBirthDate(LocalDate.of(1970, 11, 14));
		owner.setGender(0);
		owner.setEmail("george@gmail.com");
		owner.setTelephone("6085551023");

		BDDMockito.given(this.ownerService.findOwnerById(RentalHistoryControllerTests.TEST_OWNER_ID)).willReturn(owner);

		Rental rental = new Rental();
		rental.setId(RentalHistoryControllerTests.RENTAL_ID);
		rental.setStartDate(LocalDate.of(2020, 06, 1));
		rental.setEndDate(LocalDate.of(2020, 06, 10));
		rental.setPriceMonth(200.);
		rental.setIsARequest(false);
		rental.setIsAccepted(true);
		rental.setRoom(room);
		rental.setStudent(student);
		rental.setOwner(owner);

		BDDMockito.given(this.rentalService.findRentalById(RentalHistoryControllerTests.RENTAL_ID)).willReturn(rental);

	}
	@WithMockUser(value = "spring")
	@Test
	void testInitRentalList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rentals")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("rental")).andExpect(MockMvcResultMatchers.view().name("rentals/rentalsList"));
	}
}
