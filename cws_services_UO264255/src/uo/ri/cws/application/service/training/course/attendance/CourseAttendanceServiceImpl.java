package uo.ri.cws.application.service.training.course.attendance;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.training.CourseAttendanceService;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.service.training.EnrollmentDto;
import uo.ri.cws.application.service.training.course.attendance.commands.FindAllActiveMechanics;
import uo.ri.cws.application.util.command.CommandExecutor;

public class CourseAttendanceServiceImpl implements CourseAttendanceService {

	CommandExecutor executor = Factory.executor.forExecutor();
	
	@Override
	public EnrollmentDto registerNew(EnrollmentDto dto)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAttendace(String id) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EnrollmentDto> findAttendanceByCourseId(String id)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseDto> findAllActiveCourses() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MechanicDto> findAllActiveMechanics() throws BusinessException {
		return executor.execute(new FindAllActiveMechanics());
	}

}
