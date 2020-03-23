package org.springframework.samples.petclinic.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Properties;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.samples.petclinic.service.PropertyService;

import org.springframework.context.ApplicationContext;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PropertyServiceTests {

//	@Autowired
//	private OwnerService ownerService;
	
	@Autowired
	protected PropertyService propertyService;

	private final static Integer TEST_PROPERTY_ID = 1;
	
	@Test
	void shouldFindAPropertyWithCorrectId() {
		Property property = this.propertyService.findPropertyById(TEST_PROPERTY_ID);
		
		assertThat(property.getAddress()).isEqualTo("Base Militar de Rotas");
		assertThat(property.getTotalRooms()).isEqualTo(4);
	}
	
	@Test
	void shouldSaveAProperty() {
		Collection<Property> propertiesBeforeAdd = this.propertyService.findAll();
		Integer sizeOfPropertiesBeforeAdd = propertiesBeforeAdd.size();
		
		Property property = new Property();
		property.setAddress("121321321");
		property.setCity("Sevilla");		
		property.setDescription("Piso cercano a la facultad de medicina");			
		property.setPropertyType(0);
		property.setSurface(90);
		property.setTotalRooms(3);
		
		this.propertyService.saveProperty(property);
		
		Collection<Property> propertiesAfterAdd =  this.propertyService.findAll();
		Integer sizeOfPropertiesAfterAdd = propertiesAfterAdd.size();
		
		assertThat(sizeOfPropertiesAfterAdd).isEqualTo(sizeOfPropertiesBeforeAdd+1);
	}
	
	@Test
	void shouldFindAllProperties() {
		Collection<Property> properties = this.propertyService.findAll();

		Property property1 = EntityUtils.getById(properties, Property.class, TEST_PROPERTY_ID);
		assertThat(property1.getAddress()).isEqualTo("Base Militar de Rotas");
		Property property3 = EntityUtils.getById(properties, Property.class, TEST_PROPERTY_ID+2);
		assertThat(property3.getAddress()).isEqualTo("Base Militar de Jotas");
		
	}
	
	@Test
	void shouldDeleteAProperty() {
		Collection<Property> propertiesBeforeDelete = this.propertyService.findAll();
		Integer sizeOfPropertiesBeforeDelete = propertiesBeforeDelete.size();
		
		Property property = this.propertyService.findPropertyById(TEST_PROPERTY_ID);
		this.propertyService.deleteProperty(property);
		
		Collection<Property> propertiesAfterDelete =  this.propertyService.findAll();
		Integer sizeOfPropertiesAfterDelete = propertiesAfterDelete.size();
		
		assertThat(sizeOfPropertiesAfterDelete).isEqualTo(sizeOfPropertiesBeforeDelete-1);

	}
//	@Test
//	@Transactional
//	public void shouldInsertPropertyIntoDatabaseAndGenerateId() {
//		//Owner owner1 = this.ownerService.findOwnerById(1);
//		
//		//Contar el número de habitaciones del propietario
//		//int found = owner1.getProperties().size();
//
//		Property property = new Property();
//		property.setId(TEST_PROPERTY_ID);
//		property.setAddress("Dirección");
//		property.setCity("Ciudad");
//		property.setDescription("Descripcion");
//		property.setPropertyType(0);
//		property.setSurface(45);
//		property.setTotalRooms(3);
//		
//		//Añadir la habitación al Owner
//		//owner1.addProperty(property);
//		
//		//assertThat(owner1.getProperties().size()).isEqualTo(found + 1);
//
//            try {
//                this.propertyService.saveProperty(property);
//            } catch (DuplicatedPetNameException ex) {
//                Logger.getLogger(PropertyServiceTests.class.getName()).log(Level.SEVERE, null, ex);
//            }
//		//this.ownerService.saveOwner(owner1);
//
//		//owner1 = this.ownerService.findOwnerById(1);
//		//assertThat(owner1.getProperty().size()).isEqualTo(found + 1);
//		// checks that id has been generated
//		//assertThat(pet.getId()).isNotNull();
//	}
}
