package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TVOUCHERS")
public class Voucher extends PaymentMean {
	@Column(unique = true)
	private String code;
	private double available;
	private String description;

	Voucher() {
	}

	public Voucher(String code) {
		Argument.isNotEmpty(code);
		this.code = code;
	}

	public Voucher(String code, double d, String description) {
		this(code);
		Argument.isTrue(d > 0);
		Argument.isNotEmpty(description);
		this.available = d;
		this.description = description;
	}

	public Voucher(String string, double d) {
		this(string);
		Argument.isTrue(d > 0);
		this.available = d;
	}

	public String getCode() {
		return code;
	}

	public double getDisponible() {
		return available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescripcion(String description) {
		this.description = description;

	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available
				+ ", description=" + description + ", getAccumulated()="
				+ getAccumulated() + "]";
	}

	/**
	 * Augments the accumulated and decrements the available
	 * 
	 * @throws IllegalStateException
	 *             if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		if (available >= amount) {
			this.setAccumulated(getAccumulated() + amount);
			this.available -= amount;
		} else
			throw new IllegalStateException("Not enough money");
	}

}
