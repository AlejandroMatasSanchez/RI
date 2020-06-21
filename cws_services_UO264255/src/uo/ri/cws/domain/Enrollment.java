package uo.ri.cws.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TENROLLMENTS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "MECHANIC_ID", "COURSE_ID" }) })
public class Enrollment extends BaseEntity {

	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Course course;
	private int attendance;
	private boolean passed;

	Enrollment() {
	}

	public Enrollment(Mechanic mechanic, Course course, int attendance,
			boolean passed) {
		Argument.isNotNull(mechanic);
		Argument.isNotNull(course);
		Argument.isTrue(attendance >= 85);
		Argument.isNotNull(passed);
		this.attendance = attendance;
		this.passed = passed;
		Associations.Enroll.link(mechanic, this, course);
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Course getCourse() {
		return course;
	}

	void _setCourse(Course course) {
		this.course = course;
	}

	public int getAttendance() {
		return attendance;
	}

	public boolean isPassed() {
		return passed;
	}

	@Override
	public String toString() {
		return "Enrollment [mechanic=" + mechanic + ", course=" + course
				+ ", attendance=" + attendance + ", passed=" + passed + "]";
	}

	public int getAttendedHoursFor(VehicleType vt) {
		int res = 0;
		Set<Dedication> percentage = course.getDedications();
		for (Dedication dedication : percentage) {
			if (dedication.getVehicleType().equals(vt)) {
				res = attendance * dedication.getPercentage() / 100;
				break;
			}
		}
		return res;
	}
}
