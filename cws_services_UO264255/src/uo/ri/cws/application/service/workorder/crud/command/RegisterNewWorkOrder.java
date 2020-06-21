package uo.ri.cws.application.service.workorder.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class RegisterNewWorkOrder implements Command<WorkOrderDto> {

	private WorkOrderDto wDto;
	private WorkOrderRepository wRepo = Factory.repository.forWorkOrder();
	private VehicleRepository vRepo = Factory.repository.forVehicle();

	public RegisterNewWorkOrder(WorkOrderDto dto) {
		this.wDto = dto;
	}

	@Override
	public WorkOrderDto execute() throws BusinessException {
		checkDto(wDto);

		Vehicle toRepair = vRepo.findById(wDto.vehicleId).get();
		WorkOrder workOrder = new WorkOrder(toRepair, wDto.description);
		
		wRepo.add(workOrder);

		wDto.id = workOrder.getId();
		return wDto;
	}

	private void checkDto(WorkOrderDto dto) throws BusinessException {
		BusinessCheck.isNotEmpty(dto.description, "Empty description");
		BusinessCheck.isNotEmpty(dto.vehicleId, "Empty vehicle id");
	}

}
