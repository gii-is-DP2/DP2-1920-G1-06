
package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(controllers = RoomController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class RoomControllerTests {

	private static final int	TEST_ROOM_ID	= 1;
	
	private static final int TEST_PROPERTY_ID = 1;

	@Autowired
	private RoomController		roomController;

	@MockBean
	private RoomService			roomService;
	
	@MockBean
	private PropertyService			propertyService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private UserService userService;
	
	private Room room;
	
	private Property property;
	
	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() {
		
		Property property = new Property();
		property.setId(TEST_PROPERTY_ID);

		room = new Room();
		room.setId(RoomControllerTests.TEST_ROOM_ID);
		room.setRoomNumber("3");
		room.setSurface(2000);
		room.setPrice(3.0);
		room.setExtWindow(0);
		room.setTamCloset(4);
		
		room.setProperty(property);


		given(this.roomService.findRoomById(TEST_ROOM_ID)).willReturn(room);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/properties/{propertyId}/rooms/new",TEST_PROPERTY_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("room"))
		.andExpect(view().name("rooms/createOrUpdateRoomForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/properties/{propertyId}/rooms/new",TEST_PROPERTY_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("roomNumber", "3")
				.param("surface", "2000")
				.param("price", "3.0")
				.param("extWindow", "0")
				.param("tamCloset", "4"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms/new").with(csrf())
				.param("roomNumber", "4")
				.param("surface", "Hola")
				.param("price", "asd2")
				.param("extWindow", "asd")
				.param("tamCloset", "asdg"))
			.andExpect(status().isOk())
			.andExpect(model()
			.attributeHasErrors("room")).andExpect(model()
			.attributeHasFieldErrors("room", "surface"))
			.andExpect(model().attributeHasFieldErrors("room", "price"))
			.andExpect(model().attributeHasFieldErrors("room", "extWindow"))
			.andExpect(model().attributeHasFieldErrors("room", "tamCloset"))
			.andExpect(view().name("rooms/createOrUpdateRoomForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testInitUpdateRoomForm() throws Exception {
		mockMvc.perform(get("/rooms/{roomId}/edit", TEST_ROOM_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("room"))
				.andExpect(model().attribute("room", hasProperty("roomNumber", is("3"))))
				.andExpect(model().attribute("room", hasProperty("surface", is(2000))))
				.andExpect(model().attribute("room", hasProperty("price", is(3.0))))
				.andExpect(model().attribute("room", hasProperty("extWindow", is(0))))
				.andExpect(model().attribute("room", hasProperty("tamCloset",is(4))))
				.andExpect(view().name("rooms/createOrUpdateRoomForm"));
	}
    
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateRoomFormSuccess() throws Exception {
		this.mockMvc.perform(post("properties/{propertyId}/rooms/{roomId}/edit",TEST_PROPERTY_ID, TEST_ROOM_ID).with(csrf())
				.param("roomNumber", "3")
				.param("surface", "2000")
				.param("price", "3.0")
			.param("extWindow", "0")
			.param("tamCloset", "4"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:properties/" + TEST_PROPERTY_ID +"/rooms/"+TEST_ROOM_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateRoomFormHasErrors() throws Exception {
		this.mockMvc
			.perform(post("/room/{roomId}/edit",TEST_ROOM_ID).with(csrf())
					.param("roomNumber", "4")
					.param("surface", "Hola")
					.param("price", "asd2")
					.param("extWindow", "asd")
				.param("tamCloset", "asdg"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("room"))
			.andExpect(model().attributeHasFieldErrors("room", "surface"))
			.andExpect(model().attributeHasFieldErrors("room", "price"))
			.andExpect(model().attributeHasFieldErrors("room", "extWindow"))
			.andExpect(model().attributeHasFieldErrors("room", "tamCloset"))
			.andExpect(view().name("rooms/createOrUpdateRoomForm"));

	}

}
