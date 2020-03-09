package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person{
	
	// Relaciones ------------------------------------------
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
	private Set<Rental> rental;

}
