/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Simple JavaBean domain object representing an person.
 *
 * @author Ken Krebs
 */

@MappedSuperclass
public class Person extends BaseEntity {

	// Atributos -------------------------------------------------------

	@Column(name = "first_name")
	@NotEmpty
	protected String firstName;

	@Column(name = "last_name")
	@NotEmpty
	protected String lastName;

	@Column(name = "dni")
	@Pattern(regexp = "(\\d{8})([A-Z]{1})")
	protected String dni;

	@Column(name = "birth_date")
	@Past()
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected LocalDate birthDate;

	@Column(name = "gender")
	@Range(max = 1, min = 0)
	protected Integer gender;

	@Column(name = "email")
	@Email
	protected String email;

	@Column(name = "telephone")
	@Pattern(regexp = "^([0-9]{9})$")
	private String telephone;

	@Transient
	public String getGenderToString() {
		String result = "";
		if (this.gender == 0) {
			result = "MALE";
		} else if (this.gender == 1) {
			result = "FEMALE";
		}
		return result;
	}
	
	

	// Getters y Setters -----------------------------------------------

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	

	public LocalDate getBirthDate() {
		return birthDate;
	}



	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}



	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
