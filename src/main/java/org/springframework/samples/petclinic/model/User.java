package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User{
	
	
	@Id
	@Column(unique=true)
	@NotEmpty
	String username;
	
	@NotEmpty
	String password;
	
	boolean enabled;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "student_id")
//	private Student student;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "owner_id")
//	private Owner owner;
	
	
	
}
