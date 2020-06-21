package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCOURSES")
public class Course extends BaseEntity {

	@Column(unique = true)
	private String code;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private int hours;

	@OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST)
	private Set<Dedication> dedications = new HashSet<>();

	@OneToMany(mappedBy = "course")
	private Set<Enrollment> enrollments = new HashSet<>();

	Course() {
	}

	public Course(String string) {
		Argument.isNotEmpty(string);
		this.code = string;
	}

	public Course(String code, String name, String description, Date startDate,
			Date endDate, int duration) {
		this(code);
		Argument.isNotEmpty(name);
		Argument.isNotEmpty(description);
		Argument.isNotNull(startDate);
		Argument.isNotNull(endDate);
		Argument.isTrue(duration > 0);
		Argument.isTrue(startDate.before(endDate));
		this.name = name;
		this.description = description;
		this.startDate = new Date(startDate.getTime());
		this.endDate = new Date(endDate.getTime());
		this.hours = duration;
	}
	
	public Course(String code, String name, String description, Date startDate,
			Date endDate, int duration, Map<VehicleType, Integer> percentages) {
		this(code, name, description, startDate, endDate, duration);
		addDedications(percentages);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return new Date(startDate.getTime());
	}

	public Date getEndDate() {
		return new Date(endDate.getTime());
	}

	public int getHours() {
		return hours;
	}

	public Set<Dedication> getDedications() {
		return new HashSet<>(dedications);
	}

	Set<Dedication> _getDedications() {
		return dedications;
	}

	public Set<Enrollment> getEnrollments() {
		return new HashSet<>(enrollments);
	}

	Set<Enrollment> _getEnrollments() {
		return enrollments;
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", description="
				+ description + ", startDate=" + startDate + ", endDate="
				+ endDate + ", hours=" + hours + "]";
	}

	public void addDedications(Map<VehicleType, Integer> percentages) {
		int temp = 0;
		if (dedications.size() > 0)
			throw new IllegalStateException(
					"There are already dedications in this course");

		for (Integer p : percentages.values()) {
			temp += p;
		}
		if (temp != 100)
			throw new IllegalArgumentException(
					"The total percentage is different from 100");

		for (Map.Entry<VehicleType, Integer> percentage : percentages
				.entrySet()) {
			dedications.add(new Dedication(percentage.getKey(),
					percentage.getValue(), this));
		}
	}

	public void clearDedications() {
		for (Dedication dedication : new HashSet<>(dedications)) {
			Associations.Dedicate.unlink(dedication);
		}
		dedications.clear();
	}

}
