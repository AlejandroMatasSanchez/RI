package uo.ri.cws.domain;

public class Associations {

	public static class Own {

		public static void link(Client client, Vehicle vehicle) {
			vehicle._setClient(client);
			client._getVehicles().add(vehicle);
		}

		public static void unlink(Client client, Vehicle vehicle) {
			client._getVehicles().remove(vehicle);
			vehicle._setClient(null);
		}

	}

	public static class Classify {

		public static void link(VehicleType vehicleType, Vehicle vehicle) {
			vehicle._setVehicleType(vehicleType);
			vehicleType._getVehicles().add(vehicle);
		}

		public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
			vehicleType._getVehicles().remove(vehicle);
			vehicle._setVehicleType(null);
		}

	}

	public static class Pay {

		public static void link(Client client, PaymentMean payMean) {
			payMean._setClient(client);
			client._getPayMeans().add(payMean);
		}

		public static void unlink(Client client, PaymentMean payMean) {
			client._getPayMeans().remove(payMean);
			payMean._setClient(null);
		}

		public static void link(PaymentMean payMean, Client client) {
			link(client, payMean);
		}
	}

	public static class Order {

		public static void link(Vehicle vehicle, WorkOrder workOrder) {
			workOrder._setVehicle(vehicle);
			vehicle._getWorkOrders().add(workOrder);
		}

		public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
			vehicle._getWorkOrders().remove(workOrder);
			workOrder._setVehicle(null);
		}
	}

	public static class ToInvoice {
		public static void link(Invoice invoice, WorkOrder workOrder) {
			workOrder._setInvoice(invoice);
			invoice._getWorkorders().add(workOrder);
		}

		public static void unlink(Invoice invoice, WorkOrder workOrder) {
			invoice._getWorkorders().remove(workOrder);
			workOrder._setInvoice(null);
		}
	}

	public static class Charges {

		public static void link(Invoice invoice, Charge charge,
				PaymentMean paymentMean) {
			charge._setInvoice(invoice);
			charge._setPaymentMean(paymentMean);
			invoice._getCharges().add(charge);
			paymentMean._getCharges().add(charge);
		}

		public static void unlink(Charge charge) {
			charge.getInvoice()._getCharges().remove(charge);
			charge.getPaymentMean()._getCharges().remove(charge);
			charge._setInvoice(null);
			charge._setPaymentMean(null);
		}

	}

	public static class Assign {

		public static void link(Mechanic mechanic, WorkOrder workOrder) {
			workOrder._setMechanic(mechanic);
			mechanic._getWorkorders().add(workOrder);
		}

		public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
			mechanic._getWorkorders().remove(workOrder);
			workOrder._setMechanic(null);
		}

	}

	public static class Intervene {

		public static void link(WorkOrder w, Intervention i, Mechanic m) {
			i._setMechanic(m);
			i._setWorkOrder(w);
			w._getInterventions().add(i);
			m._getInterventions().add(i);
		}

		public static void unlink(Intervention i) {
			i.getMechanic()._getInterventions().remove(i);
			i.getWorkOrder()._getInterventions().remove(i);
			i._setMechanic(null);
			i._setWorkOrder(null);
		}
	}

	public static class Sustitute {

		public static void link(SparePart sP, Substitution s, Intervention i) {
			s._setIntervention(i);
			s._setSparePart(sP);
			sP._getSubstitutions().add(s);
			i._getSubstitutions().add(s);
		}

		public static void unlink(Substitution s) {
			s.getIntervention()._getSubstitutions().remove(s);
			s.getSparePart()._getSubstitutions().remove(s);
			s._setIntervention(null);
			s._setSparePart(null);
		}

	}

	public static class Dedicate {

		public static void link(VehicleType vt, Dedication dedication,
				Course course) {
			dedication._setVehicleType(vt);
			dedication._setCourse(course);
			vt._getDedications().add(dedication);
			course._getDedications().add(dedication);

		}

		public static void unlink(Dedication dedication) {
			dedication.getVehicleType()._getDedications().remove(dedication);
			dedication.getCourse()._getDedications().remove(dedication);
			dedication._setVehicleType(null);
			dedication._setCourse(null);

		}

	}

	public static class Qualifies {

		public static void link(Mechanic mechanic, Certificate certificate,
				VehicleType vehicleType) {
			certificate._setMechanic(mechanic);
			certificate._setVehicleType(vehicleType);
			mechanic._getCertificates().add(certificate);
			vehicleType._getCertificates().add(certificate);
		}

		public static void unlink(Certificate certificate) {
			certificate.getMechanic()._getCertificates().remove(certificate);
			certificate.getVehicleType()._getCertificates().remove(certificate);
			certificate._setMechanic(null);
			certificate._setVehicleType(null);
		}
	}

	public static class Enroll {

		public static void link(Mechanic mechanic, Enrollment enrollment,
				Course course) {
			enrollment._setMechanic(mechanic);
			enrollment._setCourse(course);
			mechanic._getEnrollments().add(enrollment);
			course._getEnrollments().add(enrollment);
		}

	}

}
