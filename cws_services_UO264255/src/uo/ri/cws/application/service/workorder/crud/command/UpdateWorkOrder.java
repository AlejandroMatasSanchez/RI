package uo.ri.cws.application.service.workorder.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class UpdateWorkOrder implements Command<Void> {

	private WorkOrderDto woDto;
	private WorkOrderRepository woRepo = Factory.repository.forWorkOrder();

	public UpdateWorkOrder(WorkOrderDto dto) {
		this.woDto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessCheck.isNotEmpty(woDto.description, "Description is empty");
		BusinessCheck.isTrue(woDto.status == "OPEN" || woDto.status == "ASSIGNED");

		WorkOrder workOrder = woRepo.findById(woDto.id).get();

		workOrder.setDescription(woDto.description);
		return null;
	}

}
