package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TWORKORDERS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "VEHICLE_ID", "INVOICE_ID",
		"DATE" }) })
public class WorkOrder extends BaseEntity {

	public enum WorkOrderStatus {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	private Date date;
	private String description;
	private double amount = 0.0;
	@Enumerated(EnumType.STRING)
	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	@ManyToOne
	private Vehicle vehicle;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();
	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Invoice invoice;

	WorkOrder() {

	}

	public WorkOrder(Date date, Vehicle vehicle) {
		Argument.isNotNull(date);
		Argument.isNotNull(vehicle);
		this.date = new Date(date.getTime());
		Associations.Order.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(new Date(), vehicle);
		Argument.isNotEmpty(description);
		this.description = description;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description
				+ ", amount=" + amount + ", status=" + status.toString() + "]";
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is
	 * called from Invoice.addWorkOrder(...)
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not FINISHED, or - The work order is
	 *             not linked with the invoice
	 */
	public void markAsInvoiced() {
		if (this.status != WorkOrderStatus.FINISHED || getInvoice() == null)
			throw new IllegalStateException("There is not an assigned invoice");

		status = WorkOrderStatus.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in ASSIGNED state, or - The work
	 *             order is not linked with a mechanic
	 */
	public void markAsFinished() {
		if (!this.status.equals(WorkOrderStatus.ASSIGNED)
				|| getMechanic() == null)
			throw new IllegalStateException("The workorder is not assigned");
		amount = 0;
		status = WorkOrderStatus.FINISHED;
		for (Intervention intervention : interventions) {
			amount += intervention.getAmount();
		}

	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method
	 * is called from Invoice.removeWorkOrder(...)
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not INVOICED, or - The work order is
	 *             still linked with the invoice
	 */
	public void markBackToFinished() {
		if (status != WorkOrderStatus.INVOICED || getInvoice() == null)
			throw new IllegalStateException("The work order is not invoiced");
		status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in OPEN status, or - The work
	 *             order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		if (status != WorkOrderStatus.OPEN || getMechanic() != null)
			throw new IllegalStateException("The work order is not open");

		Associations.Assign.link(mechanic, this);
		status = WorkOrderStatus.ASSIGNED;

	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * status back to OPEN
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in ASSIGNED status
	 */
	public void desassign() {
		if (status == WorkOrderStatus.ASSIGNED) {
			Associations.Assign.unlink(mechanic, this);
			status = WorkOrderStatus.OPEN;
		} else {
			throw new IllegalStateException("The work order is not assigned");
		}
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be
	 * moved back to OPEN state and unlinked from the previous mechanic.
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in FINISHED status
	 */
	public void reopen() {
		if (status == WorkOrderStatus.FINISHED) {
			Associations.Assign.unlink(mechanic, this);
			status = WorkOrderStatus.OPEN;
		} else {
			throw new IllegalStateException("The work order is not finished");
		}
	}
}
