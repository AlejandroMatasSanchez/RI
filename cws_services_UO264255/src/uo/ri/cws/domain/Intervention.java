package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TINTERVENTIONS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "WORKORDER_ID", "MECHANIC_ID",
		"DATE" }) })
public class Intervention extends BaseEntity {
	@ManyToOne
	private WorkOrder workOrder;
	@ManyToOne
	private Mechanic mechanic;

	private Date date;
	private int minutes;
	@OneToMany(mappedBy = "intervention")
	private Set<Substitution> substitutions = new HashSet<>();

	Intervention() {
	}

	public Intervention(WorkOrder workOrder, Mechanic mechanic, Date date) {
		this.date = new Date(date.getTime());
		Associations.Intervene.link(workOrder, this, mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int mins) {
		this(workOrder, mechanic, new Date());
		Argument.isTrue(mins >= 0);
		this.minutes = mins;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Date getDate() {
		return date;
	}

	public int getMinutes() {
		return minutes;
	}

	@Override
	public String toString() {
		return "Intervention [workOrder=" + workOrder + ", mechanic=" + mechanic
				+ ", date=" + date + ", minutes=" + minutes + ", substitutions="
				+ substitutions + "]";
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public double getAmount() {
		double amount = 0;
		for (Substitution substitution : substitutions) {
			amount += substitution.getImporte();
		}

		amount += workOrder.getVehicle().getVehicleType().getPricePerHour()
				* (minutes / 60.0);
		return amount;
	}

}
