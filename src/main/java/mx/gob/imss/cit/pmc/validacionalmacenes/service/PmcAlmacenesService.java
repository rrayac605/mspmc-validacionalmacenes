package mx.gob.imss.cit.pmc.validacionalmacenes.service;

import java.util.List;

import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual.CuentaIndividualVo;
import mx.gob.imss.sisec.cuentaindividual.HistoricoCuentaIndividualVo;

public interface PmcAlmacenesService {

	List<CuentaIndividualVo> obtenerInformacionCI(String nss) throws BusinessException;
	
	List<HistoricoCuentaIndividualVo> obtenerInformacionHistorico(String nss) throws BusinessException;

}
