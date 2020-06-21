package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TSUBSTITUTIONS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "SPAREPART_ID",
		"INTERVENTION_ID" }) })
public class Substitution extends BaseEntity {
	@ManyToOne
	private SparePart sparePart;
	@ManyToOne
	private Intervention intervention;
	private int quantity;

	Substitution() {
	}

	public Substitution(SparePart sparePart, Intervention intervention) {
		Argument.isNotNull(sparePart);
		Argument.isNotNull(intervention);
		Associations.Sustitute.link(sparePart, this, intervention);

	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		this(sparePart, intervention);
		Argument.isTrue(quantity > 0);
		this.quantity = quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getImporte() {
		return quantity * sparePart.getPrice();
	}

	@Override
	public String toString() {
		return "Substitution [quantity=" + quantity + "]";
	}
}
