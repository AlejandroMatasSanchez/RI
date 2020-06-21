package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;

import uo.ri.cws.application.repository.EnrollmentRepository;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class EnrollmentJpaRepository extends BaseJpaRepository<Enrollment>
implements EnrollmentRepository {

	@Override
	public List<Enrollment> findForMechanicAndPassed(String id) {
		return Jpa.getManager()
				.createNamedQuery("Enrollment.findByMechanicIdAndPassed",
						Enrollment.class)
				.setParameter(1, id).getResultList();
	}

	@Override
	public List<Enrollment> findEnrollmentsByMechanicId(String id) {
		return Jpa.getManager()
				.createNamedQuery("Enrollment.findByMechanicId",
						Enrollment.class)
				.setParameter(1, id).getResultList();
	}

}
