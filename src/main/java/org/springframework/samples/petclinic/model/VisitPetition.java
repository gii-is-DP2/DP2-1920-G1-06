package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "visitPetitions")
public class VisitPetition extends BaseEntity {
	

	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "comment")
	@NotEmpty
	private String comment;
	
	
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private Apartment apartment;
	
	
}
