package uo.ri.cws.application.service.workorder.assign;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.workorder.AssignWorkOrderService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.service.workorder.assign.commands.AssignWorkOrderToMechanic;
import uo.ri.cws.application.service.workorder.assign.commands.FindCertificatesByVehicleType;
import uo.ri.cws.application.util.command.CommandExecutor;

public class AssignWorkOrderServiceImpl implements AssignWorkOrderService {

	CommandExecutor executor = Factory.executor.forExecutor();
	
	@Override
	public void assignWorkOrderToMechanic(String woId, String mechanicId)
			throws BusinessException {
		 executor.execute(new AssignWorkOrderToMechanic(woId, mechanicId));
	}

	@Override
	public List<CertificateDto> findCertificatesByVehicleTypeId(String id)
			throws BusinessException {
		return executor.execute(new FindCertificatesByVehicleType(id));
	}

	@Override
	public List<WorkOrderDto> findUnfinishedWorkOrders()
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
