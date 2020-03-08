package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "xvisits")
public class Xvisit {

	
	//fecha y hora, comentarios. 
	
	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	@NotEmpty
	
	private LocalDate startDate;
	
	@Column(name = "time")
	@NotEmpty
	
	private String time;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private Room room;
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "comment")
	private String comment;
	
	
	
	
	
}
