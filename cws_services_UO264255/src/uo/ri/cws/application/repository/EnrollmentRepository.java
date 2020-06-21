package uo.ri.cws.application.repository;

import java.util.List;

import uo.ri.cws.domain.Enrollment;

public interface EnrollmentRepository {

	/**
	 * Find all the enrollments for the mechanic provided
	 */
	public List<Enrollment> findForMechanicAndPassed(String id);

	public List<Enrollment> findEnrollmentsByMechanicId(String id);

}
