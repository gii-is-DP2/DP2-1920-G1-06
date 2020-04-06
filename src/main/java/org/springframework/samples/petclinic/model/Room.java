package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity{
	
	// Atributos ----------------------------------------------------
	
	@Column(name = "roomNumber")
	@Length(min = 1,max = 30)
	private String	roomNumber;

	@Column(name = "surface")
	@Range(min = 1, max = 50)
	private Integer	surface;

	@Column(name = "price")
	@Range(min = 1)
	private Double	price;

	@Column(name = "extWindow")
	@Range(min = 0)
	private Integer	extWindow;

	@Column(name = "tamCloset")
	@Range(min = 0, max = 20)
	private Integer	tamCloset;
	
	// Relaciones -----------------------------------------------

	@ManyToOne
	@JoinColumn(name = "property_id")
	public Property property;


	//Setters

	public void setRoomId(final String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setSurface(final Integer surface) {
		this.surface = surface;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public void setExtWindow(final Integer extWindow) {
		this.extWindow = extWindow;
	}

	public void setTamCloset(final Integer tamCloset) {
		this.tamCloset = tamCloset;
	}

}
