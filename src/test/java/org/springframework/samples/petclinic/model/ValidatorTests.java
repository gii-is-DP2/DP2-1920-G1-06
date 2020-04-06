package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class ValidatorTests {
	
	private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
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
