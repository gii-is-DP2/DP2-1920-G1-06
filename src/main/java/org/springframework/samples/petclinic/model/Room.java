package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity{
	
	// Atributos ----------------------------------------------------
	
	@Column(name = "roomNumber")
	private String	roomNumber;

	@Column(name = "surface")
	private Integer	surface;

	@Column(name = "price")
	private Double	price;

	@Column(name = "extWindow")
	private Integer	extWindow;

	@Column(name = "tamCloset")
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
