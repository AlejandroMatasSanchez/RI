package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TVEHICLETYPES")
public class VehicleType extends BaseEntity {

	@Column(unique = true)
	private String name;
	private double pricePerHour;

	@OneToMany(mappedBy = "vehicleType")
	private Set<Vehicle> vehicles = new HashSet<>();

	@OneToMany(mappedBy = "vehicleType")
	private Set<Dedication> dedications = new HashSet<>();
	@OneToMany(mappedBy = "vehicleType")
	private Set<Certificate> certificates = new HashSet<>();

	private int minTrainingHours;

	VehicleType() {

	}

	public VehicleType(String name) {
		Argument.isNotEmpty(name);
		this.name = name;
	}

	public VehicleType(String name, double pricePerHour) {
		this(name);
		Argument.isNotNull(pricePerHour);
		this.pricePerHour = pricePerHour;
	}

	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public int getMinTrainingHours() {
		return minTrainingHours;
	}

	public void setMinTrainingHours(int minTrainingHours) {
		this.minTrainingHours = minTrainingHours;
	}

	public Set<Dedication> getDedications() {
		return new HashSet<>(dedications);
	}

	Set<Dedication> _getDedications() {
		return dedications;
	}

	public Set<Certificate> getCertificates() {
		return new HashSet<>(certificates);
	}

	Set<Certificate> _getCertificates() {
		return certificates;
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
				+ ", minTrainingHours="
				+ minTrainingHours + "]";
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

}
