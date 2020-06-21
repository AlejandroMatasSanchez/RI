package uo.ri.cws.application.service.training.course.crud.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CourseRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.VehicleType;

public class AddCourse implements Command<CourseDto> {

	private CourseRepository cRepo = Factory.repository.forCourse();
	private VehicleTypeRepository vtRepo = Factory.repository.forVehicleType();
	private CourseDto cDto;

	public AddCourse(CourseDto dto) {
		this.cDto = dto;
	}

	@Override
	public CourseDto execute() throws BusinessException {

		checkDto(cDto);
		checkDoesNotExist(cDto.code);

		Map<VehicleType,Integer> percentages = new HashMap<VehicleType,Integer>();
		cDto.percentages.forEach((name, perc) -> 
		{
			percentages.put(vtRepo.findById(name).get(), perc);
		});
		
		Course c = new Course(cDto.code, cDto.name, cDto.description,
				cDto.startDate, cDto.endDate, cDto.hours, percentages);

		cRepo.add(c);

		cDto.id = c.getId();
		return cDto;
	}

	private void checkDto(CourseDto dto) throws BusinessException {
		BusinessCheck.isNotEmpty(dto.code, "The code is empty");
		BusinessCheck.isNotEmpty(dto.name, "The name is empty");
		BusinessCheck.isNotEmpty(dto.description, "The description is empty");
		BusinessCheck.isNotNull(dto.startDate, "The start date is empty");
		BusinessCheck.isNotNull(dto.endDate, "The end date is empty");
		BusinessCheck.isNotNull(dto.hours, "The hours are empty");
		checkPercentages(dto);
	}

	private void checkPercentages(CourseDto dto) throws BusinessException {
		int temp = 0;
		if (dto.percentages.size() == 0)
			throw new BusinessException(
					"There are no dedications in this course");

		for (Integer p : dto.percentages.values()) {
			temp += p;
		}
		if (temp != 100)
			throw new BusinessException(
					"The total percentage is different from 100");

	}
	private void checkDoesNotExist(String code) throws BusinessException {
		Optional<Course> oc = cRepo.findByCode(code);
		BusinessCheck.isFalse(oc.isPresent(), "The course already exists");
	}

}
