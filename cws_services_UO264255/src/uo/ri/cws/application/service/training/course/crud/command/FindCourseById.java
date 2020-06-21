package uo.ri.cws.application.service.training.course.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class FindCourseById implements Command<Optional<CourseDto>> {

	private CourseRepository cRepo = Factory.repository.forCourse();
	private String id;
	
	public FindCourseById(String cId) {
		this.id = cId;
	}

	@Override
	public Optional<CourseDto> execute() throws BusinessException {
		Optional<Course> course = cRepo.findById(id);
		
		return course.map(c -> DtoAssembler.toDto(c));
	}

}
