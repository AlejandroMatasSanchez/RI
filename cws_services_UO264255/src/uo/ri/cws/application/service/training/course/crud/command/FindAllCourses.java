package uo.ri.cws.application.service.training.course.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class FindAllCourses implements Command<List<CourseDto>> {

	private CourseRepository cRepo = Factory.repository.forCourse();
	@Override
	public List<CourseDto> execute() throws BusinessException {
		List<Course> courses = cRepo.findAll();
		return DtoAssembler.toCourseDtoList(courses);
	}

}
