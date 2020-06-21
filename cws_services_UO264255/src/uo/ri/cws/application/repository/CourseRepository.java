package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Course;

public interface CourseRepository extends Repository<Course> {

	Optional<Course> findByCode(String code);

	List<Course> findAll();

}
