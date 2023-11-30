package mx.gob.imss.cit.pmc.validacionalmacenes.service;

import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;

public interface PmcAseguradoService {

	AseguradoDTO existeAsegurado(String nssAsegurado) throws BusinessException;

}
