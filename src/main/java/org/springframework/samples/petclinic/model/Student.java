package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person{
	
	// Relaciones ------------------------------------------
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
	private Set<Rental> rental;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lastname", referencedColumnName = "last_name")
	private Person person;

	public Set<Rental> getRental() {
		return rental;
	}

	public void setRental(Set<Rental> rental) {
		this.rental = rental;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}


}
