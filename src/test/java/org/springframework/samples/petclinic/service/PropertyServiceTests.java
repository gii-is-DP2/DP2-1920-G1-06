package org.springframework.samples.petclinic.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.samples.petclinic.service.PropertyService;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PropertyServiceTests {

	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	protected PropertyService propertyService;

	private final static Integer TEST_PROPERTY_ID = 1;
	@Test
	void shouldFindAProperWithCorrectId() {
		Property property = this.propertyService.findPropertyById(1);
		
		assertThat(property.getAddress()).isEqualTo("Base Militar de Rotas");
		assertThat(property.getTotalRooms()).isEqualTo(4);
	}
	
	@Test
	void shouldFindAllProperties() {
		Collection<Property> properties = this.propertyService.findAll();

		Property property1 = EntityUtils.getById(properties, Property.class, 1);
		assertThat(property1.getAddress()).isEqualTo("Base Militar de Rotas");
		Property property3 = EntityUtils.getById(properties, Property.class, 3);
		assertThat(property3.getAddress()).isEqualTo("Base Militar de Jotas");
	}
	
	@Test
	@Transactional
	public void shouldInsertPropertyIntoDatabaseAndGenerateId() {
		//Owner owner1 = this.ownerService.findOwnerById(1);
		
		//Contar el número de habitaciones del propietario
		//int found = owner1.getProperties().size();

		Property property = new Property();
		property.setId(TEST_PROPERTY_ID);
		property.setAddress("Dirección");
		property.setCity("Ciudad");
		property.setDescription("Descripcion");
		property.setPropertyType(0);
		property.setSurface(45);
		property.setTotalRooms(3);
		
		//Añadir la habitación al Owner
		//owner1.addProperty(property);
		
		//assertThat(owner1.getProperties().size()).isEqualTo(found + 1);

//            try {
//                this.propertyService.saveProperty(property);
//            } catch (DuplicatedPetNameException ex) {
//                Logger.getLogger(PropertyServiceTests.class.getName()).log(Level.SEVERE, null, ex);
//            }
		//this.ownerService.saveOwner(owner1);

		//owner1 = this.ownerService.findOwnerById(1);
		//assertThat(owner1.getProperty().size()).isEqualTo(found + 1);
		// checks that id has been generated
		//assertThat(pet.getId()).isNotNull();
	}
}
