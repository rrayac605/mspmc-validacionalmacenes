package mx.gob.imss.cit.pmc.validacionalmacenes.service.impl;

import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import mx.gob.imss.cit.mspmccommons.dto.PatronDTO;
import mx.gob.imss.cit.mspmccommons.dto.PatronJsonDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.utils.DigitoVerificadorUtils;
import mx.gob.imss.cit.pmc.validacionalmacenes.service.PmcPatroneService;

@Component
public class PmcPatroneServiceImpl implements PmcPatroneService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@Override
	public PatronDTO obtenerPatron(String regPatronal) throws BusinessException {
		ResponseEntity<PatronJsonDTO> response = null;
		PatronDTO patronDTO = null;
		@SuppressWarnings("resource")
		Formatter fmt = new Formatter();
		try {
			if ((regPatronal != null && !regPatronal.trim().equals("")) && regPatronal.length() == 10) {
				regPatronal = regPatronal.concat(DigitoVerificadorUtils.generaDigitoVerificadorRP(regPatronal));
			}
			response = restTemplate.getForEntity(env.getProperty("urlPatronMSValicacionAlmacenes").concat(regPatronal), PatronJsonDTO.class);
		} catch (RestClientException e) {
			System.out.println("No existe información: " + regPatronal);
		}
		if (response != null) {			
			try {
				patronDTO = new PatronDTO();
				patronDTO.setCveClase(response.getBody().getClasificacion().getFraccion().getClase().getClave());
				patronDTO.setDesClase(response.getBody().getClasificacion().getFraccion().getClase().getDescripcion());
				patronDTO.setCveFraccion((response.getBody().getClasificacion().getFraccion().getId()));
				patronDTO.setDesFraccion((response.getBody().getClasificacion().getFraccion().getDescripcion()));
				patronDTO.setCveDelRegPatronal(response.getBody().getSubdelegacion().getDelegacion().getClave());
				patronDTO.setDesDelRegPatronal(response.getBody().getSubdelegacion().getDelegacion().getDescripcion());
				patronDTO.setCveSubDelRegPatronal(response.getBody().getSubdelegacion().getClave());
				patronDTO.setDesSubDelRegPatronal(response.getBody().getSubdelegacion().getDescripcion());
				patronDTO.setNumPrima(response.getBody().getClasificacion().getPrimaSRTActual());
				if (response.getBody().getMoral() != null) {
					patronDTO.setDesRazonSocial(response.getBody().getMoral().getRazonSocial());
					patronDTO.setDesRfc(response.getBody().getMoral().getRfc());
				}
				else if (response.getBody().getFisica() != null) {
					patronDTO.setDesRazonSocial(response.getBody().getFisica().getNombre());
					patronDTO.setDesRfc(response.getBody().getFisica().getRfc());
				}
				patronDTO.setRefRegistroPatronal(regPatronal);
				patronDTO.setCveDelegacionAux(String
						.valueOf(fmt.format("%02d", response.getBody().getSubdelegacion().getDelegacion().getId())));
			} catch (Exception e) {
				logger.error("Error obtenerPatron:", e);
				throw new BusinessException(e);
			}
		}
		return patronDTO;
	}

}
