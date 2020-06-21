package uo.ri.cws.application.service.training.course.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class DeleteCourse implements Command<Void> {

	private CourseRepository cRepo = Factory.repository.forCourse();
	private String id;

	public DeleteCourse(String id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {

		Optional<Course> course = cRepo.findById(id);
		BusinessCheck.exists(course, "The course does not exist");
		BusinessCheck.isTrue(course.get().getEnrollments().isEmpty(),
				"There are mechanic enrolled in this course");
		course.get().clearDedications();
		cRepo.remove(course.get());
		return null;
	}
}
