package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "owners")
public class Owner extends Person {

	// Relaciones ------------------------------------------

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Property> properties;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;


	public Set<Property> getProperties() {
		return properties;
	}

	public void setProperties(Set<Property> properties) {
		this.properties = properties;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
