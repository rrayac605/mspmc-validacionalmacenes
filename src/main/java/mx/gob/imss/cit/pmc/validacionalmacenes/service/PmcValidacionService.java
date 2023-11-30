package mx.gob.imss.cit.pmc.validacionalmacenes.service;

import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.PatronDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.validacionalmacenes.input.ValidacionLocalInput;

public interface PmcValidacionService {

	DetalleRegistroDTO validaRegistro(ValidacionLocalInput input) throws BusinessException;
	
	PatronDTO buscar(ValidacionLocalInput input) throws BusinessException;

}
