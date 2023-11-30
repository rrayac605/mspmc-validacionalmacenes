package mx.gob.imss.cit.pmc.validacionalmacenes.service.impl;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual.CuentaIndividualVo;
import mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual.RespuestaCuentaIndividual;
import mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual.WSCuentaIndividualSISEC;
import mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual.WSCuentaIndividualSISEC_Service;
import mx.gob.imss.cit.pmc.validacionalmacenes.service.PmcAlmacenesService;
import mx.gob.imss.sisec.cuentaindividual.HistoricoCuentaIndividualVo;
import mx.gob.imss.sisec.cuentaindividual.RespuestaHistoricoCuentaIndividual;
import mx.gob.imss.sisec.cuentaindividual.WSHistoricoCentralSISEC;
import mx.gob.imss.sisec.cuentaindividual.WSHistoricoCentralSISEC_Service;

@Component
public class PmcAlmacenesServiceImpl implements PmcAlmacenesService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Environment env;

	@Override
	public List<CuentaIndividualVo> obtenerInformacionCI(String nss) throws BusinessException {

		List<CuentaIndividualVo> cuentaIndividual = null;
		try {
			URL url = new URL(env.getProperty("urlCIMSValidacionAlmacenes"));
			QName qname = new QName(env.getProperty("nameSpaceCIMSValidacionAlmacenes"),
					env.getProperty("qNameCIMSValidacionAlmacenes"));
			logger.info("Invocando WS cuentaIndividual url:[{}], namespace:[{}], qname:[{}]",
					env.getProperty("urlCIMSValidacionAlmacenes"), env.getProperty("nameSpaceCIMSValidacionAlmacenes"),
					env.getProperty("qNameCIMSValidacionAlmacenes"));
			WSCuentaIndividualSISEC_Service service = new WSCuentaIndividualSISEC_Service(url, qname);
			WSCuentaIndividualSISEC wsCuentaIndividualSISEC = service.getWSCuentaIndividualSISECPort();

			RespuestaCuentaIndividual respuesta = wsCuentaIndividualSISEC.getCuentaIndividualSISEC(nss);

			cuentaIndividual = respuesta.getCuentaIndividual();

		} catch (Exception e) {
			logger.error("Error obtenerInformacionCI:", e);
			throw new BusinessException(e);
		}
		return cuentaIndividual;
	}

	@Override
	public List<HistoricoCuentaIndividualVo> obtenerInformacionHistorico(String nss) throws BusinessException {
		List<HistoricoCuentaIndividualVo> historico = null;
		try {

			URL url = new URL(env.getProperty("urlHistoricoMSValidacionAlmacenes"));
			QName qname = new QName(env.getProperty("nameSpaceHistoricoMSValidacionAlmacenes"),
					env.getProperty("qNameHistoricoMSValidacionAlmacenes"));
			logger.info("Invocando WS historico url:[{}], namespace:[{}], qname:[{}]",
					env.getProperty("urlHistoricoMSValidacionAlmacenes"), env.getProperty("nameSpaceHistoricoMSValidacionAlmacenes"),
					env.getProperty("qNameHistoricoMSValidacionAlmacenes"));
			WSHistoricoCentralSISEC_Service service = new WSHistoricoCentralSISEC_Service(url, qname);
			WSHistoricoCentralSISEC wsHistoricoCentralSISEC = service.getWSHistoricoCentralSISECPort();

			RespuestaHistoricoCuentaIndividual respuesta = wsHistoricoCentralSISEC.getHistoricoCentralSISEC(nss);

			historico = respuesta.getHistcuentaIndividual();

		} catch (Exception e) {
			logger.error("Error obtenerInformacionHistorico:", e);
			throw new BusinessException(e);
		}
		return historico;
	}

}
