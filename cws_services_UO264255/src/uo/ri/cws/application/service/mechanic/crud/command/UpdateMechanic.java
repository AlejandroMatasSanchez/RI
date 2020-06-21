package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class UpdateMechanic implements Command<Void>{

	private MechanicDto dto;

	private MechanicRepository repo = Factory.repository.forMechanic();

	
	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		BusinessCheck.isNotEmpty(dto.name, "Name is empty");
		BusinessCheck.isNotEmpty(dto.surname, "Surname is empty");

		Optional<Mechanic> om = repo.findById(dto.id);
		BusinessCheck.exists(om, "The mechanic does not exist");
		BusinessCheck.hasVersion(om.get(), dto.version);
		Mechanic m = om.get();
		
		//This is persistent so the mapper updates it.
		m.setName(dto.name);
		m.setSurname(dto.surname);
		return null;
	}

}
