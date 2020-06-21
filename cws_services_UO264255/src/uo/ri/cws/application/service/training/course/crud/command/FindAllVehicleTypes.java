package uo.ri.cws.application.service.training.course.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.VehicleType;

public class FindAllVehicleTypes implements Command<List<VehicleTypeDto>> {

	private VehicleTypeRepository vtRepo = Factory.repository.forVehicleType();

	@Override
	public List<VehicleTypeDto> execute() throws BusinessException {
		List<VehicleType> res = new ArrayList<>();

		res = vtRepo.findAll();

		return DtoAssembler.toVehicleTypeDtoList(res);
	}

}
