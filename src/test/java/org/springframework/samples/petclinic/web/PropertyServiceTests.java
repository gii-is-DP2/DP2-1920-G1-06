package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PropertyController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
public class PropertyServiceTests {

	private static final int TEST_OWNER_ID = 1;
	
	private Property property;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PropertyService clinicService;
	
	@BeforeEach
	void setup() {

		property = new Property();
		property.setId(TEST_OWNER_ID);
		property.setAddress("");
		property.setCity("");
		property.setDescription("");
		property.setPropertyType(0);
		property.setSurface(45);
		property.setTotalRooms(3);
		//given(this.clinicService.findPropertyById(TEST_OWNER_ID)).willReturn(property);

	}


}
