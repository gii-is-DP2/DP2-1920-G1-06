package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	
	
	//------------------- Property -------------------------

	
	@Test
	void shouldNotValidateWhenAddressLengthLessThanFive() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("");
		property.setCity("Sevilla");		
		property.setDescription("Piso cercano a la facultad de medicina");			
		property.setPropertyType(0);
		property.setSurface(90);
		property.setTotalRooms(3);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("address");
		assertThat(violation.getMessage()).isEqualTo("length must be between 5 and 100");
	}
	
	@Test
	void shouldNotValidateWhenAddressLengthMoreThanHundred() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("Avenida no se que de luca de tena, estoy intentando llegar a 100 caracteres para hacer esta prueba ea por fin");
		property.setCity("Sevilla");		
		property.setDescription("Piso cercano a la facultad de medicina");			
		property.setPropertyType(0);
		property.setSurface(90);
		property.setTotalRooms(3);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("address");
		assertThat(violation.getMessage()).isEqualTo("length must be between 5 and 100");
	}
	
	@Test
	void shouldNotValidateWhenCityEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("");		
		property.setDescription("Piso cercano a la facultad de medicina");			
		property.setPropertyType(0);
		property.setSurface(90);
		property.setTotalRooms(3);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("city");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenPropertyTypeLessThanZero() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("Piso cercano a la facultad de medicina");			
		property.setPropertyType(-3);
		property.setSurface(90);
		property.setTotalRooms(3);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("propertyType");
		assertThat(violation.getMessage()).isEqualTo("must be between 0 and 1");
	}
	
	@Test
	void shouldNotValidateWhenPropertyTypeMoreThanOne() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("Piso cercano a la facultad de medicina");			
		property.setPropertyType(8);
		property.setSurface(90);
		property.setTotalRooms(3);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("propertyType");
		assertThat(violation.getMessage()).isEqualTo("must be between 0 and 1");
	}
	
	@Test
	void shouldNotValidateWhenDescriptionEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("");			
		property.setPropertyType(1);
		property.setSurface(90);
		property.setTotalRooms(3);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenTotalRoomsLessThanOne() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("una casa muy bonita");			
		property.setPropertyType(1);
		property.setSurface(90);
		property.setTotalRooms(0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("totalRooms");
		assertThat(violation.getMessage()).isEqualTo("must be between 1 and 100");
	}
	
	@Test
	void shouldNotValidateWhenTotalRoomsMoreThanHundred() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("una casa muy bonita");			
		property.setPropertyType(1);
		property.setSurface(90);
		property.setTotalRooms(101);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("totalRooms");
		assertThat(violation.getMessage()).isEqualTo("must be between 1 and 100");
	}
	
	@Test
	void shouldNotValidateWhenSurfaceLessThirty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("una casa muy bonita");			
		property.setPropertyType(1);
		property.setSurface(20);
		property.setTotalRooms(4);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("surface");
		assertThat(violation.getMessage()).isEqualTo("must be between 30 and 1000");
	}
	
	@Test
	void shouldNotValidateWhenSurfaceMoreThanThousand() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("una casa muy bonita");			
		property.setPropertyType(1);
		property.setSurface(1020);
		property.setTotalRooms(4);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Property> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("surface");
		assertThat(violation.getMessage()).isEqualTo("must be between 30 and 1000");
	}
	
	@Test
	void PositiveModel() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Property property = new Property();
		property.setAddress("mi casa");
		property.setCity("Sevilla");		
		property.setDescription("una casa muy bonita");			
		property.setPropertyType(1);
		property.setSurface(100);
		property.setTotalRooms(4);

		Validator validator = createValidator();
		Set<ConstraintViolation<Property>> constraintViolations = validator.validate(property);

		assertThat(constraintViolations.size()).isEqualTo(0);
	}

  
  
  
  //------------------- Room -------------------------
		@Test
		void shouldNotValidateRoomNumber() {

			LocaleContextHolder.setLocale(Locale.ENGLISH);
			Room room = new Room();
			room.setRoomNumber("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
			room.setSurface(10);		
			room.setPrice(100.0);			
			room.setExtWindow(0);
			room.setTamCloset(9);

			Validator validator = createValidator();
			Set<ConstraintViolation<Room>> constraintViolations = validator.validate(room);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Room> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("roomNumber");
			assertThat(violation.getMessage()).isEqualTo("length must be between 1 and 30");
		}


		@Test
		void shouldNotValidateSurface() {

			LocaleContextHolder.setLocale(Locale.ENGLISH);
			Room room = new Room();
			room.setRoomNumber("3");
			room.setSurface(100);		
			room.setPrice(100.0);			
			room.setExtWindow(0);
			room.setTamCloset(9);

			Validator validator = createValidator();
			Set<ConstraintViolation<Room>> constraintViolations = validator.validate(room);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Room> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("surface");
			assertThat(violation.getMessage()).isEqualTo("must be between 1 and 50");
		}



		@Test
		void shouldNotValidatePrice() {

			LocaleContextHolder.setLocale(Locale.ENGLISH);
			Room room = new Room();
			room.setRoomNumber("3");
			room.setSurface(10);		
			room.setPrice(-100.0);			
			room.setExtWindow(0);
			room.setTamCloset(9);

			Validator validator = createValidator();
			Set<ConstraintViolation<Room>> constraintViolations = validator.validate(room);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Room> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
			assertThat(violation.getMessage()).isEqualTo("must be between 1 and 9223372036854775807");
		}
		

		@Test
		void shouldNotValidateExtWindow() {

			LocaleContextHolder.setLocale(Locale.ENGLISH);
			Room room = new Room();
			room.setRoomNumber("3");
			room.setSurface(10);		
			room.setPrice(100.0);			
			room.setExtWindow(-1);
			room.setTamCloset(9);

			Validator validator = createValidator();
			Set<ConstraintViolation<Room>> constraintViolations = validator.validate(room);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Room> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("extWindow");
			assertThat(violation.getMessage()).isEqualTo("must be between 0 and 9223372036854775807");
		}



		@Test
		void shouldNotValidateTamCloset() {

			LocaleContextHolder.setLocale(Locale.ENGLISH);
			Room room = new Room();
			room.setRoomNumber("3");
			room.setSurface(10);		
			room.setPrice(100.0);			
			room.setExtWindow(0);
			room.setTamCloset(100);

			Validator validator = createValidator();
			Set<ConstraintViolation<Room>> constraintViolations = validator.validate(room);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Room> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("tamCloset");
			assertThat(violation.getMessage()).isEqualTo("must be between 0 and 20");
		}



		@Test
		void shouldValidateRoom() {

			LocaleContextHolder.setLocale(Locale.ENGLISH);
			Room room = new Room();
			room.setRoomNumber("3");
			room.setSurface(10);		
			room.setPrice(100.0);			
			room.setExtWindow(0);
			room.setTamCloset(9);

			Validator validator = createValidator();
			Set<ConstraintViolation<Room>> constraintViolations = validator.validate(room);

			assertThat(constraintViolations.size()).isEqualTo(0);
		}
}
