package mx.gob.imss.cit.pmc.validacionalmacenes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import mx.gob.imss.cit.mspmccommons.dto.BitacoraErroresDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.ParametroDTO;
import mx.gob.imss.cit.mspmccommons.dto.PatronDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.utils.DateUtils;
import mx.gob.imss.cit.mspmccommons.utils.DigitoVerificadorUtils;
import mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual.CuentaIndividualVo;
import mx.gob.imss.cit.pmc.validacionalmacenes.input.ValidacionLocalInput;
import mx.gob.imss.cit.pmc.validacionalmacenes.integration.repository.ParametroRepository;
import mx.gob.imss.cit.pmc.validacionalmacenes.service.PmcAlmacenesService;
import mx.gob.imss.cit.pmc.validacionalmacenes.service.PmcValidacionService;
import mx.gob.imss.sisec.cuentaindividual.HistoricoCuentaIndividualVo;

@Component
public class PmcValidacionServiceImpl implements PmcValidacionService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PmcAlmacenesService pmcAlmacenesService;

	@Autowired
	private ParametroRepository parametroRepository;

	@Override
	public DetalleRegistroDTO validaRegistro(ValidacionLocalInput input) throws BusinessException {
		List<BitacoraErroresDTO> errores = new ArrayList<BitacoraErroresDTO>();
		DetalleRegistroDTO detalle = new DetalleRegistroDTO();
		Date fecha = input.getFechaAccidente() != null && !input.getFechaAccidente().trim().equals("")
				? DateUtils.parserFromString(input.getFechaAccidente())
				: DateUtils.parserFromString(input.getFechaInicio());
		if (input.isCambioNss() || input.isCambioRp()) {
			logger.info("Validando cambios");
			BitacoraErroresDTO errorRelacionLaboral = validarRelacionLaboral(input.getRp(), input.getNss(), fecha);
			if (errorRelacionLaboral != null) {
				errores.add(errorRelacionLaboral);
			}
		}
		if (!errores.isEmpty()) {
			detalle.setBitacoraErroresDTO(errores);

		}
		return detalle;
	}

	@Override
	public PatronDTO buscar(ValidacionLocalInput input) throws BusinessException {
		logger.info("buscar RP");
		PatronDTO patron = null;
		RestTemplate restTemplate = new RestTemplate();
		Optional<ParametroDTO> urlUpdateMovto = parametroRepository.findOneByCve("urlPatronPMC");
		String rp = buscarRelacionLaboral(input.getNss(), input.getFechaAccidente());
		if (rp != null && !rp.isEmpty()) {
			input.setRp(rp + DigitoVerificadorUtils.generaDigitoVerificadorRP(rp));
			HttpEntity<ValidacionLocalInput> request1 = new HttpEntity<>(input);
			DetalleRegistroDTO response1 = restTemplate.postForObject(urlUpdateMovto.get().getDesParametro(), request1,
					DetalleRegistroDTO.class);
			if (response1 != null && response1.getPatronDTO() != null) {
				patron = response1.getPatronDTO();
			}
		}
		return patron;
	}

	private BitacoraErroresDTO validarRelacionLaboral(String rp, String nss, Date fechaAccidente)
			throws BusinessException {
		logger.info("Validando validarRelacionLaboral");
		BitacoraErroresDTO bitacoraErroresDTO = null;
		boolean existeRelacion = false;
		List<CuentaIndividualVo> cis = pmcAlmacenesService.obtenerInformacionCI(nss);
		for (CuentaIndividualVo ci : cis) {
			if (rp.substring(0, 8).equals(ci.getRegistroPatronal()) && nss.equals(ci.getNss())
					&& (DateUtils.parserFromStringCuentaIndividual(ci.getFechaInicioMovimiento())
							.compareTo(fechaAccidente) <= 0
							&& DateUtils.parserFromStringCuentaIndividual(ci.getFechaFinalMovimiento())
									.compareTo(fechaAccidente) >= 0)) {
				existeRelacion = true;
				break;
			}
		}
		if (!existeRelacion) {
			List<HistoricoCuentaIndividualVo> historicos = pmcAlmacenesService.obtenerInformacionHistorico(nss);
			for (HistoricoCuentaIndividualVo historico : historicos) {
				if (rp.equals(historico.getRegistroPatronal()) && nss.equals(historico.getNss())) {
					existeRelacion = true;
					break;
				}
			}
		}
		if (!existeRelacion) {
			logger.info("No existe relacion laboral");
			bitacoraErroresDTO = new BitacoraErroresDTO();
			bitacoraErroresDTO.setDesCodigoError("No existe relaci\u00F3n laboral");
		}
		return bitacoraErroresDTO;
	}

	private String buscarRelacionLaboral(String nss, String fechaAccidente) throws BusinessException {
		logger.info("Validando validarRelacionLaboral");
		String rp = null;
		List<CuentaIndividualVo> cis = pmcAlmacenesService.obtenerInformacionCI(nss);
		for (CuentaIndividualVo ci : cis) {
			if (DateUtils.parserFromStringCuentaIndividual(ci.getFechaInicioMovimiento())
					.compareTo(DateUtils.parserFromString(fechaAccidente)) <= 0
					&& DateUtils.parserFromStringCuentaIndividual(ci.getFechaFinalMovimiento())
							.compareTo(DateUtils.parserFromString(fechaAccidente)) >= 0) {
				rp = ci.getRegistroPatronal() + ci.getClaveModalidad();
				break;
			}
		}
		if (rp != null) {
			List<HistoricoCuentaIndividualVo> historicos = pmcAlmacenesService.obtenerInformacionHistorico(nss);
			for (HistoricoCuentaIndividualVo historico : historicos) {
				if (DateUtils.parserFromStringCuentaIndividual(historico.getFechaInicioMovimiento())
						.compareTo(DateUtils.parserFromString(fechaAccidente)) >= 0
						&& DateUtils.parserFromStringCuentaIndividual(historico.getFechaFinalMovimiento())
								.compareTo(DateUtils.parserFromString(fechaAccidente)) <= 0) {
					rp = historico.getRegistroPatronal() + historico.getClaveModalidad();
					break;
				}
			}
		}
		return rp;
	}

}
