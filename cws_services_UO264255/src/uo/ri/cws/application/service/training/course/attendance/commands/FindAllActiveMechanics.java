package uo.ri.cws.application.service.training.course.attendance.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindAllActiveMechanics implements Command<List<MechanicDto>> {

	private MechanicRepository mRepo = Factory.repository.forMechanic();
	
	@Override
	public List<MechanicDto> execute() throws BusinessException {
		List<Mechanic> mechanics = new ArrayList<Mechanic>();
		List<MechanicDto> mList = new ArrayList<MechanicDto>();
		
		mechanics = mRepo.findAll();
		for (Mechanic mechanic : mechanics) {
			if(!mechanic.getEnrollments().isEmpty())
				mList.add(DtoAssembler.toDto(mechanic));
		}
		return mList;
	}

}
