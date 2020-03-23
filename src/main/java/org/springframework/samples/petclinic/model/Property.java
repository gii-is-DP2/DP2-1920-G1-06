package org.springframework.samples.petclinic.model;

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

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "properties")
public class Property extends BaseEntity {

	// Atributos --------------------------------------------------------

	@Column(name = "address")
	@NotEmpty
	private String address;

	@Column(name = "city")
	@NotEmpty
	private String city;

	@Column(name = "propertyType")
	@NotEmpty
	@Range(max = 1, min = 0)
	private Integer propertyType;

	@Column(name = "description")
	@NotEmpty
	private String description;

	@Column(name = "totalRooms")
	@NotEmpty
	@Range(min = 1)
	private Integer totalRooms;

	@Column(name = "surface")
	@NotEmpty
	@Range(min = 30)
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

	// HAY QUE HACERLA !!!!!!!!!!
	@Transient
	public Integer getAvailableRooms() {
		return totalRooms;
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

}
