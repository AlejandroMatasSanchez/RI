package uo.ri.cws.application.service.vehicle.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;

public class FindVehicleById implements Command<Optional<VehicleDto>> {

	private String vId;
	private VehicleRepository vRepo = Factory.repository.forVehicle();
	
	public FindVehicleById(String vehicleId) {
		this.vId = vehicleId;
	}

	@Override
	public Optional<VehicleDto> execute() throws BusinessException {
		
		Optional<Vehicle> vehicle = vRepo.findById(vId);
		
		return vehicle.map(v -> DtoAssembler.toDto(vehicle.get()));
	}

}
