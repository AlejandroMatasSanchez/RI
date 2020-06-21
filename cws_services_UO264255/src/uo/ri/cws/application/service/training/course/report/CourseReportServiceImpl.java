package uo.ri.cws.application.service.training.course.report;

import java.util.List;

import alb.util.exception.NotYetImplementedException;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.training.CourseReportService;
import uo.ri.cws.application.service.training.TrainingForMechanicRow;
import uo.ri.cws.application.service.training.TrainingHoursRow;
import uo.ri.cws.application.util.command.CommandExecutor;

public class CourseReportServiceImpl implements CourseReportService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<TrainingForMechanicRow> findTrainigByMechanicId(String id)
			throws BusinessException {
		return executor.execute(new FindTrainingByMechanicId(id));
	}

	@Override
	public List<TrainingHoursRow> findTrainingByVehicleTypeAndMechanic()
			throws BusinessException {
		throw new NotYetImplementedException();
	}

	@Override
	public List<CertificateDto> findCertificatedByVehicleType()
			throws BusinessException {
		return executor.execute(new FindCertificatedByVehicleType());
	}

}
