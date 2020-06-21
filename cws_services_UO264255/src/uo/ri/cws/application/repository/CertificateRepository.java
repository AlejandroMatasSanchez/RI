package uo.ri.cws.application.repository;

import java.util.List;

import uo.ri.cws.domain.Certificate;

public interface CertificateRepository extends Repository<Certificate> {

	public List<Certificate>findByVehicleType(String id);

	public List<Certificate> findAll();
}
