package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCHARGES", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "SPAREPART_ID",
		"INTERVENTION_ID" }) })
public class Charge extends BaseEntity {

	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;
	private double amount;

	Charge() {
	}

	public Invoice getInvoice() {
		return invoice;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		this.amount = amount;
		Argument.isNotNull(invoice);
		Argument.isNotNull(paymentMean);
		Argument.isTrue(amount > 0);
		// store the amount
		this.amount = amount;
		// increment the paymentMean accumulated ( paymentMean.pay( -amount) )
		paymentMean.pay(amount);
		// link invoice, this and paymentMean
		Associations.Charges.link(invoice, this, paymentMean);
	}

	/**
	 * Unlinks this charge and restores the value to the payment mean
	 * 
	 * @throws IllegalStateException
	 *             if the invoice is already settled
	 */
	public void rewind() {
		// assert the invoice is not in PAID status
		// decrement the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlink invoice, this and paymentMean
	}

}
