package uo.ri.cws.application.service.training.course.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.TrainingForMechanicRow;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Dedication;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.VehicleType;

public class FindTrainingByMechanicId
		implements Command<List<TrainingForMechanicRow>> {

	private String mId;
	private VehicleTypeRepository vtRepo = Factory.repository.forVehicleType();
	private EnrollmentRepository eRepo = Factory.repository.forEnrollment();

	public FindTrainingByMechanicId(String id) {
		this.mId = id;
	}

	@Override
	public List<TrainingForMechanicRow> execute() throws BusinessException {
		List<TrainingForMechanicRow> rows = new ArrayList<TrainingForMechanicRow>();
		List<VehicleType> vtList = new ArrayList<VehicleType>();
		List<Enrollment> eList = new ArrayList<Enrollment>();

		vtList = vtRepo.findAll();
		eList = eRepo.findEnrollmentsByMechanicId(mId);

		for (VehicleType type : vtList) {
			TrainingForMechanicRow row = new TrainingForMechanicRow();
			row.vehicleTypeName = type.getName();
			for (Enrollment enrollment : eList) {
				Set<Dedication> dedications = enrollment.getCourse()
						.getDedications();
				dedications.forEach((d) -> {
					if (d.getVehicleType().equals(type))
						row.enrolledHours += enrollment.getCourse().getHours()
								* (d.getPercentage() / 100.0);
				});

				row.attendedHours += enrollment.getAttendedHoursFor(type);
			}
			rows.add(row);
		}
		return rows;
	}

}
