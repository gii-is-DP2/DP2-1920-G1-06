package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "rentals")

public class Rental extends BaseEntity {

	// Atributos ---------------------------------------------

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Column(name = "startDate")
	//@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;

	@Column(name = "endDate")
	//@Future
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;

	@Column(name = "priceMonth")
	private Double priceMonth;
	
	@Column(name = "isARequest") //ES solicitud (true) O respuesta (False)
	private Boolean isARequest;
	
	@Column(name = "isAccepted")
	private Boolean isAccepted;

	public Boolean getIsARequest() {
		return isARequest;
	}

	public void setIsARequest(Boolean isARequest) {
		this.isARequest = isARequest;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Transient
	public Integer getTotalPrice() {
		// Si alquilas en diciebre y acabas el contrato en enero el resultado es -11 
		//TODO
		Integer months = this.endDate.getMonthValue() - this.startDate.getMonthValue();

		return (int) (this.priceMonth * months);
	}

	// Relaciones ------------------------------------------

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id")
	private Room room;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id")
	private Owner owner;

	
	
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

	public Double getPriceMonth() {
		return priceMonth;
	}

	public void setPriceMonth(Double priceMonth) {
		this.priceMonth = priceMonth;
	}

}