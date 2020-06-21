package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TMECHANICS")
public class Mechanic extends BaseEntity {
	@Column(unique = true)
	private String dni;
	private String surname;
	private String name;

	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> workorders = new HashSet<>();

	@OneToMany(mappedBy = "mechanic")
	private Set<Certificate> certificates = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Enrollment> enrollments = new HashSet<>();

	Mechanic() {

	}

	public Mechanic(String dni) {
		Argument.isNotEmpty(dni);
		this.dni = dni;
	}

	public Mechanic(String dni, String name, String surname) {
		this(dni);
		Argument.isNotEmpty(name);
		Argument.isNotEmpty(surname);
		this.name = name;
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(workorders);
	}

	Set<WorkOrder> _getWorkorders() {
		return workorders;
	}

	public Set<Certificate> getCertificates() {
		return new HashSet<>(certificates);
	}

	Set<Certificate> _getCertificates() {
		return certificates;
	}

	public Set<Enrollment> getEnrollments() {
		return new HashSet<>(enrollments);
	}

	Set<Enrollment> _getEnrollments() {
		return enrollments;
	}

	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
				+ name + "]";
	}

	public Set<Enrollment> getEnrollmentsFor(VehicleType vt) {
		Set<Enrollment> res = new HashSet<>();
		if (vt != null) {
			for (Enrollment enrollment : enrollments) {
				Set<Dedication> dedications = enrollment.getCourse()
						._getDedications();
				for (Dedication dedication : dedications) {
					if (dedication.getVehicleType().equals(vt)) {
						res.add(enrollment);
						break;
					}
				}
			}
		}
		return res;
	}

	public boolean isCertifiedFor(VehicleType vt) {
		for (Certificate certificate : certificates) {
			if (certificate.getVehicleType().equals(vt)
					&& certificate.getMechanic().equals(this))
				return true;
		}
		return false;
	}

	public boolean isCertifiedFor(VehicleType vt, Date date) {
		for (Certificate certificate : certificates) {
			if (certificate.getVehicleType().equals(vt)
					&& certificate.getMechanic().equals(this)
					&& certificate.getDate().equals(date))
				return true;
		}
		return false;
	}

}
