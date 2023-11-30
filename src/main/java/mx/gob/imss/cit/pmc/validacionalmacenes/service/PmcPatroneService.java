package mx.gob.imss.cit.pmc.validacionalmacenes.service;

import mx.gob.imss.cit.mspmccommons.dto.PatronDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;

public interface PmcPatroneService {

	PatronDTO obtenerPatron(String regPatronal) throws BusinessException;

}
