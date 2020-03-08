package org.springframework.samples.petclinic.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "rentals")

public class Rental extends BaseEntity{
	//fecha inicio, fecha fin, precio del mes, total
	
	@Column(name = "startDate")
	@Temporal(TemporalType.TIMESTAMP)
	@NotEmpty
	
	private LocalDate startDate;
	
	
	@Column(name = "endDate")
	@Temporal(TemporalType.TIMESTAMP)
	@NotEmpty

	private LocalDate endDate;
	
	@Column( name = "priceMonth")
	@NotEmpty
	
	private Integer priceMonth;
	
	@Transient
	public Integer getTotalPrice() {
		
		Integer months = this.endDate.getMonthValue() - this.startDate.getMonthValue();
		
		return this.priceMonth*months;
	}
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private Room room;
	
	
	
	

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getPriceMonth() {
		return priceMonth;
	}

	public void setPriceMonth(Integer priceMonth) {
		this.priceMonth = priceMonth;
	}
	
	
	
	
	
	
	
	
	
	

}
