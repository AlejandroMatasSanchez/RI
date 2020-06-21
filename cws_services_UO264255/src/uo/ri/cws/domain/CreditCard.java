package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCREDITCARDS")
public class CreditCard extends PaymentMean {
	@Column(unique = true)
	private String number;
	private String type;
	private Date validThru;

	CreditCard() {
	}

	public CreditCard(String number) {
		Argument.isNotEmpty(number);
		this.number = number;
	}

	public CreditCard(String number, String type, Date tomorrow) {
		this(number);
		Argument.isNotEmpty(type);
		Argument.isNotNull(tomorrow);
		this.type = type;
		this.validThru = tomorrow;
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public Date getValidThru() {
		return validThru;
	}
	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type
				+ ", validThru=" + validThru + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public void pay(double amount) {
		if (new Date().before(validThru)) {
			this.setAccumulated(getAccumulated() + amount);
		} else
			throw new IllegalStateException();
	}

}
