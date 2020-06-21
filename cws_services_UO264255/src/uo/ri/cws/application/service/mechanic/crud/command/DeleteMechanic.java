package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(String idMecanico) {
		this.mechanicId = idMecanico;
	}

	@Override
	public Void execute() throws BusinessException {

		// Do the thing
		Optional<Mechanic> m = repo.findById(mechanicId);
		BusinessCheck.exists(m, "The mechanic does not exist");
		BusinessCheck.isTrue(m.get().getAssigned().isEmpty(),
				"The mechanic has assigned work");
		BusinessCheck.isTrue(m.get().getInterventions().isEmpty(),
				"The mechanic has interventions");
		repo.remove(m.get());
		return null;
	}

}
