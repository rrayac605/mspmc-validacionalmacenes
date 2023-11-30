package mx.gob.imss.cit.pmc.validacionalmacenes.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO;
import mx.gob.imss.cit.mspmccommons.dto.AseguradoJsonDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.utils.DigitoVerificadorUtils;
import mx.gob.imss.cit.pmc.validacionalmacenes.service.PmcAseguradoService;

@Component
public class PmcAseguradoServiceImpl implements PmcAseguradoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@Override
	public AseguradoDTO existeAsegurado(String nssAsegurado) throws BusinessException {
		ResponseEntity<AseguradoJsonDTO> response = null;
		AseguradoDTO aseguradoDTO = null;
		try {
			if ((nssAsegurado != null && !nssAsegurado.trim().equals("")) && nssAsegurado.length() == 10) {
				nssAsegurado = nssAsegurado.concat(DigitoVerificadorUtils.generaDigitoVerificador(nssAsegurado));
			}
			response = restTemplate.getForEntity(
					env.getProperty("urlAseguradoMSValicacionAlmacenes").concat(nssAsegurado), AseguradoJsonDTO.class);
			if (response != null) {
				aseguradoDTO = new AseguradoDTO();
				aseguradoDTO.setCveIdPersona(response.getBody().getCveIdPersona());
				aseguradoDTO.setRefCurp(response.getBody().getCurp());
				aseguradoDTO.setNomAsegurado(response.getBody().getNombre());
				aseguradoDTO.setRefPrimerApellido(response.getBody().getPrimerApellido());
				aseguradoDTO.setRefSegundoApellido(response.getBody().getSegundoApellido());
				aseguradoDTO.setNumNss(nssAsegurado);
				if (response.getBody().getSubdelegacion() != null) {
					aseguradoDTO.setCveSubdelNss(response.getBody().getSubdelegacion().getClave());
					aseguradoDTO.setDesSubDelNss(response.getBody().getSubdelegacion().getDescripcion());
					if (response.getBody().getSubdelegacion().getDelegacion() != null) {
						aseguradoDTO.setCveDelegacionNss(
								response.getBody().getSubdelegacion().getDelegacion().getClave());
						aseguradoDTO.setDesDelegacionNss(
								response.getBody().getSubdelegacion().getDelegacion().getDescripcion());
					}
				}
				if (response.getBody().getUmf() != null ) {					
					aseguradoDTO.setCveUmfAdscripcion(response.getBody().getUmf().getNumUMF());
					aseguradoDTO.setDesUmfAdscripcion(response.getBody().getUmf().getDescUMF());
				}
			}
		} catch (RestClientException e) {
			logger.error("Error existeAsegurado:", e);
			throw new BusinessException(e);
		}
		return aseguradoDTO;
	}

}
