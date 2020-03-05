package org.springframework.samples.petclinic.model;



import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;



@Entity
@Table(name = "propertys")
public class Property extends BaseEntity{

		@Column(name = "address")
		@NotEmpty
		private String address;

		@Column(name = "city")
		@NotEmpty
		private String city;
		
		@Column(name = "propertyType")
		@NotEmpty
		private PropertyType propertyType;
		
		@Column(name = "description")
		@NotEmpty
		private String description;
		
		@Column(name = "totalRooms")
		@NotEmpty
		private Integer totalRooms;
		
		@Column(name = "surface")
		@NotEmpty
		private Integer surface;
		
		
		//HAY QUE HACERLA !!!!!!!!!!
		@Transient
		public Integer getAvailableRooms() {
			return totalRooms;
		}

		@ManyToOne
		@JoinColumn(name = "xowner_id")
		private Xowner xowner;
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
		private Set<Room> rooms;
		
		//
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
		private Set<Visit> visits;
		
		
		
		

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


		public PropertyType getPropertyType() {
			return propertyType;
		}


		public void setPropertyType(PropertyType propertyType) {
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
