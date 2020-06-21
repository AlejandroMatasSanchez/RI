package uo.ri.cws.application.service.workorder.assign.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;

public class FindCertificatesByVehicleType
		implements Command<List<CertificateDto>> {

	private String vtId;
	private CertificateRepository cRepo = Factory.repository.forCertificate();

	public FindCertificatesByVehicleType(String vId) {
		this.vtId = vId;
	}

	@Override
	public List<CertificateDto> execute() throws BusinessException {
		List<Certificate> cList = new ArrayList<Certificate>();
		// We retrieve the list of certificates of a vehicle type
		cList = cRepo.findByVehicleType(vtId);

		return DtoAssembler.toCertificateDtoList(cList);
	}

}
