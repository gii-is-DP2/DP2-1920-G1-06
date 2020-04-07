package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "properties")
public class Property extends BaseEntity {

	// Atributos --------------------------------------------------------

	@Column(name = "address")
	@Length(min = 5, max = 100)
	private String address;

	@Column(name = "city")
	@NotEmpty
	private String city;

	@Column(name = "propertyType")
	@Range(max = 1, min = 0)
	private Integer propertyType;

	@Column(name = "description")
	@NotEmpty
	private String description;

	@Column(name = "totalRooms")
	@Range(min = 1, max = 100)
	@NotNull
	private Integer totalRooms;

	@Column(name = "surface")
	@Range(min = 30, max = 1000)
	@NotNull
	private Integer surface;

	@Transient
	public String getPropertyTypeToString() {
		String result = "";
		if (this.propertyType == 0) {
			result = "HOUSE";
		} else if (this.propertyType == 1) {
			result = "FLAT";
		}
		return result;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	@Transient
	public Integer getAvailableRooms() {
		//Mejorar con el contrato
		return rooms.size();
	}

	// Relaciones -------------------------------------------------------

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
	private Set<Room> rooms;

	// Setters y Getters -------------------------------------------------

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Integer propertyType) {
		this.propertyType = propertyType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(Integer totalRooms) {
		this.totalRooms = totalRooms;
	}

	public Integer getSurface() {
		return surface;
	}

	public void setSurface(Integer surface) {
		this.surface = surface;
	}
	
	
	// Metodos necesarios para la relacion con rooms
	
	protected Set<Room> getRoomsInternal() {
		if (this.rooms == null) {
			this.rooms = new HashSet<>();
		}
		return this.rooms;
	}
	
	public void addRoom(Room room) {
		getRoomsInternal().add(room);
		room.setProperty(this);
	}

}
