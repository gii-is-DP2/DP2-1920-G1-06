package org.springframework.samples.petclinic.web.e2e;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.RoomControllerTests;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional

public class RoomControllerE2ETest {
	
	private final String TEST_PROPERTY_ID = "1";
	
	private final String TEST_ROOM_ID = "1";
	
	@Autowired
	private MockMvc mockMvc;
	
	
	//TEST CREATION ------------------------------------
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/properties/{propertyId}/rooms/new", TEST_PROPERTY_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("room"))
			.andExpect(MockMvcResultMatchers.view().name("rooms/createOrUpdateRoomForm"));
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testProcessCreationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/properties/{propertyId}/rooms/new", TEST_PROPERTY_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("roomNumber", "3")
				.param("surface", "2000")
				.param("price", "3.0")
				.param("extWindow", "0")
				.param("tamCloset", "4"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//				.andExpect(MockMvcResultMatchers.view().name("redirect:/properties/{propertyId}/rooms"));
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/properties/{propertyId}/rooms/new, TEST_PROPERTY_ID")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("roomNumber", "4")
				.param("surface", "Hola")
				.param("price", "asd2")
				.param("extWindow", "asd")
				.param("tamCloset", "asdg"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("room"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "surface"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "price"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "extWindow"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "tamCloset"))
				.andExpect(MockMvcResultMatchers.view().name("rooms/createOrUpdateRoomForm"));
	}

	
	
	//TEST UPDATE ---------------------------------------------
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testProcessUpdateRoomFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/properties/{propertyId}/rooms/{roomId}/edit", TEST_PROPERTY_ID, TEST_ROOM_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("roomNumber", "3")
				.param("surface", "2000")
				.param("price", "3.0")
				.param("extWindow", "0")
				.param("tamCloset", "4"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//				.andExpect(MockMvcResultMatchers.view().name("redirect:/rooms/{roomId}"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testProcessUpdateRoomFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/properties/{propertyId}/room/{roomId}/edit", TEST_PROPERTY_ID , TEST_ROOM_ID)
					.with(SecurityMockMvcRequestPostProcessors.csrf())
					.param("roomNumber", "4")
					.param("surface", "Hola")
					.param("price", "asd2")
					.param("extWindow", "asd")
					.param("tamCloset", "asdg"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.model().attributeHasErrors("room"))
					.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "surface"))
					.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "price"))
					.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "extWindow"))
					.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("room", "tamCloset"))
					.andExpect(MockMvcResultMatchers.view().name("rooms/createOrUpdateRoomForm"));

	}
	
	
}
