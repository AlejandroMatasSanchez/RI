package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCERTIFICATES", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "MECHANIC_ID", "VEHICLETYPE_ID" }) })
public class Certificate extends BaseEntity {

	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private VehicleType vehicleType;
	private Date date;

	Certificate() {
	}

	public Certificate(Mechanic mechanic2, VehicleType vehicleType2) {
		Argument.isNotNull(mechanic2);
		Argument.isNotNull(vehicleType2);
		Associations.Qualifies.link(mechanic2, this, vehicleType2);
		this.date = new Date();
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	@Override
	public String toString() {
		return "Certificate [mechanic=" + mechanic + ", vehicleType="
				+ vehicleType + ", date=" + date + "]";
	}

}
