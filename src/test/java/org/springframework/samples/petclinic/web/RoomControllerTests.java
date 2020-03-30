
package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RoomController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class RoomControllerTests {

	private static final int	TEST_ROOM_ID	= 1;

	@Autowired
	private RoomController		roomController;

	@MockBean
	private RoomService			roomService;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() {

		Room room = new Room();
		room.setId(RoomControllerTests.TEST_ROOM_ID);
		room.setRoomNumber("3");
		room.setSurface(2000);
		room.setPrice(3.0);
		room.setExtWindow(0);
		room.setTamCloset(4);

		BDDMockito.given(this.roomService.findRoomById(RoomControllerTests.TEST_ROOM_ID)).willReturn(room);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("room"))
			.andExpect(MockMvcResultMatchers.view().name("rooms/createOrUpdateRoomForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("roomNumber", "3").param("surface", "2000").param("price", "3.0").param("extWindow", "0").param("tamCloset", "4"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("roomNumber", "4").param("surface", "Hola").param("price", "asd2").param("extWindow", "asd").param("tamCloset", "asdg"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("room")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "surface"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "price")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "extWindow"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "tamCloset")).andExpect(MockMvcResultMatchers.view().name("rooms/createOrUpdateRoomForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateRoomFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms/{roomId}/edit", RoomControllerTests.TEST_ROOM_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("roomNumber", "3").param("surface", "2000").param("price", "3.0")
			.param("extWindow", "0").param("tamCloset", "4")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/rooms/{roomId}"));
	}

	void testProcessUpdateRoomFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/room/{roomId}/edit", RoomControllerTests.TEST_ROOM_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("roomNumber", "4").param("surface", "Hola").param("price", "asd2").param("extWindow", "asd")
				.param("tamCloset", "asdg"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("room")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "surface"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "price")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "extWindow"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "tamCloset")).andExpect(MockMvcResultMatchers.view().name("rooms/createOrUpdateRoomForm"));

	}

}
