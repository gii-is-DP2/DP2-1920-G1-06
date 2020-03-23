package org.springframework.samples.petclinic.web;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.xml.HasXPath.hasXPath;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PropertyController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)

public class PropertyControllerTests {

	private static final int TEST_PROPERTY_ID = 1;

	
	@Autowired
	private PropertyController propertyController;
	
	@MockBean
	private PropertyService propertyService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {

		Property property = new Property();
		property.setId(TEST_PROPERTY_ID);
		property.setAddress("Direccion");
		property.setCity("Ciudad");
		property.setDescription("Descripcion");
		property.setPropertyType(0);
		property.setSurface(45);
		property.setTotalRooms(3);
		
		given(this.propertyService.findPropertyById(TEST_PROPERTY_ID)).willReturn(property);

	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/properties/new")).andExpect(status().isOk()).andExpect(model().attributeExists("property"))
			.andExpect(view().name("properties/createOrUpdatePropertyForm"));
}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/properties/new").with(csrf())
				.param("address", "Calle de las palmeras")
				.param("city", "London")
				.param("description", "Calle de las palmeras")
				.param("propertyType", "0")
				.param("surface","45")
				.param("totalRooms", "4"))
				.andExpect(status().is3xxRedirection());
}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/properties/new").with(csrf())
				.param("address", "Calle de las palmeras,con más de 50 caracteres,"
						+ " invariantemente de la internacionalización."
						+ "----------------------------------------------------------------"
						+ "----------------------------------------------------------------"
						+ "----------------------------------------------------------------.")
				
				.param("city", "London")
				.param("description", "Calle de las palmeras")
				.param("propertyType", "3")
				.param("surface","20")
				.param("totalRooms", "4"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("property"))
				.andExpect(model().attributeHasFieldErrors("property", "address"))
				.andExpect(model().attributeHasFieldErrors("property", "surface"))
				.andExpect(model().attributeHasFieldErrors("property", "propertyType"))
				.andExpect(view().name("properties/createOrUpdatePropertyForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitPropertiesList() throws Exception {
		mockMvc.perform(get("/properties")).andExpect(status().isOk()).andExpect(model().attributeExists("properties"))
			.andExpect(view().name("properties/propertiesList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdatePropertyForm() throws Exception {
		mockMvc.perform(get("/properties/{propertyId}/edit", TEST_PROPERTY_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("property"))
				.andExpect(model().attribute("property", hasProperty("address", is("Direccion"))))
				.andExpect(model().attribute("property", hasProperty("city", is("Ciudad"))))
				.andExpect(model().attribute("property", hasProperty("description", is("Descripcion"))))
				.andExpect(model().attribute("property", hasProperty("propertyType", is(0))))
				.andExpect(model().attribute("property", hasProperty("surface", is(45))))
				.andExpect(model().attribute("property", hasProperty("totalRooms", is(3))))
				.andExpect(view().name("properties/createOrUpdatePropertyForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePropertyFormSuccess() throws Exception {
			mockMvc.perform(post("/properties/{propertyId}/edit", TEST_PROPERTY_ID)
								.with(csrf())
								.param("address", "Calle de las palmeras")
								.param("city", "London")
								.param("description", "Calle de las palmeras")
								.param("propertyType", "0")
								.param("surface","45")
								.param("totalRooms", "4"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/properties/"+TEST_PROPERTY_ID+"/show"));
	}
	
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform(post("/properties/{propertyId}/edit", TEST_PROPERTY_ID)
							.with(csrf())
						.param("address", "Calle de las palmeras,con más de 50 caracteres,"
									+ " invariantemente de la internacionalización."
									+ "----------------------------------------------------------------"
									+ "----------------------------------------------------------------"
									+ "----------------------------------------------------------------.")
							
						.param("city", "London")
						.param("description", "Calle de las palmeras")
						.param("propertyType", "3")
						.param("surface","20")
						.param("totalRooms", "4"))
					.andExpect(status().isOk())
					.andExpect(model().attributeHasErrors("property"))
					.andExpect(model().attributeHasFieldErrors("property", "address"))
					.andExpect(model().attributeHasFieldErrors("property", "surface"))
					.andExpect(model().attributeHasFieldErrors("property", "propertyType"))
					.andExpect(view().name("properties/createOrUpdatePropertyForm"));
		
	}
	
}
