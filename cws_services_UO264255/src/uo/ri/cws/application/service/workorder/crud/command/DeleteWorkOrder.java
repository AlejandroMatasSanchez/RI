package uo.ri.cws.application.service.workorder.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class DeleteWorkOrder implements Command<Void> {

	private String woId;
	private WorkOrderRepository wRepo = Factory.repository.forWorkOrder();
	
	public DeleteWorkOrder(String id) {
		this.woId = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<WorkOrder> workOrder = wRepo.findById(this.woId);
		
		BusinessCheck.exists(workOrder, "The work order does not exist");
		BusinessCheck.isTrue(workOrder.get().getInterventions().isEmpty(),
				"The workorder has interventions");
		wRepo.remove(workOrder.get());
		return null;
	}

}
