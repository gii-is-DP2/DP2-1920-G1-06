package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "rentals")

public class Rental extends BaseEntity {
	
	// Atributos ---------------------------------------------

	@Column(name = "startDate")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotEmpty
	private LocalDate startDate;

	@Column(name = "endDate")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotEmpty
	private LocalDate endDate;

	@Column(name = "priceMonth")
	@NotEmpty
	private Integer priceMonth;

	@Transient
	public Integer getTotalPrice() {

		Integer months = this.endDate.getMonthValue() - this.startDate.getMonthValue();

		return this.priceMonth * months;
	}

	//Relaciones ------------------------------------------
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id")
	private Room room;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	private Student student;

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
