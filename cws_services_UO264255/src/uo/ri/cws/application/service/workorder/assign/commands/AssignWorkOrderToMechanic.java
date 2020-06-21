package uo.ri.cws.application.service.workorder.assign.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.WorkOrder;

public class AssignWorkOrderToMechanic implements Command<Void> {

	private String woId;
	private String mechanicId;
	private MechanicRepository mRepo = Factory.repository.forMechanic();
	private WorkOrderRepository woRepo = Factory.repository.forWorkOrder();

	public AssignWorkOrderToMechanic(String woId, String mechanicId) {
		this.woId = woId;
		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Mechanic> mechanic = mRepo.findById(mechanicId);
		Optional<WorkOrder> workOrder = woRepo.findById(woId);

		// We check that the mechanic is qualified in this type of vehicle
		if (!mechanic.get().isCertifiedFor(
				workOrder.get().getVehicle().getVehicleType())) {
			throw new BusinessException(
					"This mechanic is not qualified for this type of vehicle");
		}

		workOrder.get().assignTo(mechanic.get());
		return null;
	}

}
