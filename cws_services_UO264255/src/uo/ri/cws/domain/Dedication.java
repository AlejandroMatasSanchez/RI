package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TDEDICATIONS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "VEHICLETYPE_ID", "COURSE_ID" }) })
public class Dedication extends BaseEntity {

	@ManyToOne
	private VehicleType vehicleType;
	@ManyToOne
	private Course course;

	private int percentage;

	Dedication() {
	}

	Dedication(VehicleType vt, Integer percentage, Course course) {
		Argument.isNotNull(vt);
		Argument.isNotNull(percentage);
		Argument.isNotNull(course);
		this.percentage = percentage;
		Associations.Dedicate.link(vt, this, course);
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setVehicleType(VehicleType vt) {
		this.vehicleType = vt;
	}

	public Course getCourse() {
		return course;
	}

	void _setCourse(Course course) {
		this.course = course;
	}

	public int getPercentage() {
		return percentage;
	}

	@Override
	public String toString() {
		return "Dedication [vehicleType=" + vehicleType + ", course=" + course
				+ ", percentage=" + percentage + "]";
	}

}
