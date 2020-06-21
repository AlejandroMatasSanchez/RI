package uo.ri.cws.application.service.training.certificate.commands;

import java.util.Date;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class GenerateCertificates implements Command<Integer> {

	private MechanicRepository mRepo = Factory.repository.forMechanic();
	private VehicleTypeRepository vtRepo = Factory.repository.forVehicleType();
	private EnrollmentRepository eRepo = Factory.repository.forEnrollment();
	private CertificateRepository cRepo = Factory.repository.forCertificate();

	@Override
	public Integer execute() throws BusinessException {
		int res = 0;

		List<Mechanic> mechanics = mRepo.findAll();
		List<VehicleType> vehicleTypes = vtRepo.findAll();
		for (Mechanic mechanic : mechanics) {
			// We get the enrollments of the mechanic
			List<Enrollment> mEnrollments = eRepo
					.findForMechanicAndPassed(mechanic.getId());

			for (VehicleType vt : vehicleTypes) {
				int totalHoursFor = 0;
				for (Enrollment enrollment : mEnrollments) {
					if(enrollment.isPassed())
					totalHoursFor += enrollment.getCourse().getHours()
							* enrollment.getAttendedHoursFor(vt)/100;
					
					if (totalHoursFor >= vt.getMinTrainingHours()) {
						cRepo.add(new Certificate(mechanic, vt));
						++res;
						break;

					}
				}
			}
		}
		return res;
	}
}
