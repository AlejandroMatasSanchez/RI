package uo.ri.cws.application.service.training.course.crud.command;

import java.util.Date;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;

public class UpdateCourse implements Command<Void> {

	private CourseDto cDto;
	private CourseRepository cRepo = Factory.repository.forCourse();

	public UpdateCourse(CourseDto dto) {
		this.cDto = dto;
	}

	@Override
	public Void execute() throws BusinessException {

		checkDto(cDto);
		Optional<Course> oc = cRepo.findById(cDto.id);
		BusinessCheck.exists(oc, "The course does not exist");
		BusinessCheck.hasVersion(oc.get(), cDto.version);
		BusinessCheck.isTrue(oc.get().getStartDate().before(new Date()),
				"The course has already begun");

		Course c = oc.get();
		c.setCode(cDto.code);
		c.setName(cDto.name);
		c.setDescription(cDto.description);
		c.setStartDate(cDto.startDate);
		c.setEndDate(cDto.endDate);
		c.setHours(cDto.hours);

		return null;
	}

	private void checkDto(CourseDto dto) throws BusinessException {
		BusinessCheck.isNotEmpty(dto.code, "The code is empty");
		BusinessCheck.isNotEmpty(dto.name, "The code is empty");
		BusinessCheck.isNotEmpty(dto.description, "The code is empty");
		BusinessCheck.isNotNull(dto.startDate, "The code is empty");
		BusinessCheck.isNotNull(dto.endDate, "The code is empty");
		BusinessCheck.isNotNull(dto.hours, "The code is empty");
	}
}
