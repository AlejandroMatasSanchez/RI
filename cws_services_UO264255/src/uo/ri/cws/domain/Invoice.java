package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;
import alb.util.date.Dates;

@Entity
@Table(name = "TINVOICES")
public class Invoice extends BaseEntity {
	public enum InvoiceStatus {
		NOT_YET_PAID, PAID
	}

	@Column(unique = true)
	private Long number;

	private Date date;
	private double amount;
	private double vat;
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	@OneToMany(mappedBy = "invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany(mappedBy = "invoice")
	private Set<Charge> charges = new HashSet<>();

	Invoice() {

	}

	public Invoice(Long number, Date date) {
		Argument.isNotNull(number);
		Argument.isNotNull(date);
		this.number = number;
		this.date = new Date(date.getTime());
		// check arguments (always), through IllegalArgumentException
		// store the number
		// store a copy of the date
	}

	public Invoice(Long number) {
		this(number, new Date());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, new Date());
		Argument.isNotNull(workOrders);
		for (WorkOrder workOrder : workOrders) {
			addWorkOrder(workOrder);
		}
	}

	public Invoice(Long number, Date date, List<WorkOrder> workOrders) {
		this(number, date);
		for (WorkOrder workOrder : workOrders) {
			addWorkOrder(workOrder);
		}
	}

	/**
	 * Computed amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double temp = 0.00;
		for (WorkOrder workOrder : workOrders) {
			temp += workOrder.getAmount();
		}
		vat = Dates.fromString("1/7/2012").before(date) ? 21.00 : 18.00;

		temp += (temp * (vat / 100.00));
		this.amount = Math.round(temp * 100.00) / 100.00;
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 * 
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if (status != InvoiceStatus.NOT_YET_PAID)
			throw new IllegalStateException();
		workOrder.markBackToFinished();
		Associations.ToInvoice.unlink(this, workOrder);
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException
	 *             if - Is already settled - Or the amounts paid with charges to
	 *             payment means do not cover the total of the invoice
	 */
	public void settle() {

	}

	public Long getNumber() {
		return number;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public void setDate(Date now) {
		this.date = new Date(now.getTime());
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkorders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount="
				+ amount + ", vat=" + vat + ", status=" + status.toString() + "]";
	}

}
