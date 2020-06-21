package uo.ri.cws.application.service.training.certificate;


import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateService;
import uo.ri.cws.application.service.training.certificate.commands.GenerateCertificates;
import uo.ri.cws.application.util.command.CommandExecutor;

public class CertificateServiceImpl implements CertificateService {

	CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public int generateCertificates() throws BusinessException {
		return executor.execute(new GenerateCertificates());
	}

}
