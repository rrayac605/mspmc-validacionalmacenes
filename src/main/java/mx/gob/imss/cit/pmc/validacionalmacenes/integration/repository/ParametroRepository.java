package mx.gob.imss.cit.pmc.validacionalmacenes.integration.repository;

import java.util.Optional;

import mx.gob.imss.cit.mspmccommons.dto.ParametroDTO;


public interface ParametroRepository {

	Optional<ParametroDTO> findOneByCve(String cveIdParametro);

}
