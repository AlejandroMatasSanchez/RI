package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCASHES")
public class Cash extends PaymentMean {

	Cash() {
	}

	public Cash(Client client) {
		Argument.isNotNull(client);
		Associations.Pay.link(client, this);
	}

	@Override
	public String toString() {
		return "Cash [toString()=" + super.toString() + "]";
	}

}
