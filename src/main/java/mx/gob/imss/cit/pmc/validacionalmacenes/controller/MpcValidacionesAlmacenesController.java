package mx.gob.imss.cit.pmc.validacionalmacenes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.ErrorResponse;
import mx.gob.imss.cit.mspmccommons.dto.PatronDTO;
import mx.gob.imss.cit.mspmccommons.enums.EnumHttpStatus;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.validacionalmacenes.input.ValidacionLocalInput;
import mx.gob.imss.cit.pmc.validacionalmacenes.service.PmcValidacionService;

@RestController
@Api(value = "Validadion almacenes PMC", tags = { "Validadion almacenes PMC Rest" })
@RequestMapping("/msvalidacionAlmacenes/v1")
public class MpcValidacionesAlmacenesController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PmcValidacionService pmcValidacionService;

	@RequestMapping("/health/ready")
	@ResponseStatus(HttpStatus.OK)
	public void ready() {
	}

	@RequestMapping("/health/live")
	@ResponseStatus(HttpStatus.OK)
	public void live() {
	}

	@ApiOperation(value = "Validaciones Almacenes", nickname = "validarLocal", notes = "Validaciones Almacenes", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/validar", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object validarLocal(@RequestBody ValidacionLocalInput input,@RequestHeader(value = "Authorization") String token) {
		Object resultado = null;
		try {
			logger.info("MpcArchivosController:buscarEstadoArchivo:try [{}]", input.toString());
			DetalleRegistroDTO listado = pmcValidacionService.validaRegistro(input);
			resultado = new ResponseEntity<Object>(listado, HttpStatus.OK);
			logger.info("MpcArchivosController:buscarEstadoArchivo:returnOk");
		} catch (BusinessException be) {
			logger.info("MpcArchivosController:buscarEstadoArchivo:catch");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(),
					"Error de aplicaci\u00F3n");

			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());

			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("MpcArchivosController:buscarEstadoArchivo:numberHTTPDesired");

		}

		logger.info("MpcArchivosController:buscarEstadoArchivo:FinalReturn");
		return resultado;
	}

	@ApiOperation(value = "Obtener patron con relacion laboral", nickname = "buscarPatron", notes = "Obtener patron con relacion laboral", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/buscarPatron", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object buscar(@RequestBody ValidacionLocalInput input,@RequestHeader(value = "Authorization") String token) {
		Object resultado = null;
		try {
			logger.info("MpcArchivosController:buscarEstadoArchivo:try [{}]", input.toString());
			DetalleRegistroDTO detalleRegistroDTO = new DetalleRegistroDTO();
			PatronDTO listado = pmcValidacionService.buscar(input);
			detalleRegistroDTO.setPatronDTO(listado);
			resultado = new ResponseEntity<Object>(detalleRegistroDTO, HttpStatus.OK);
			logger.info("MpcArchivosController:buscarEstadoArchivo:returnOk");
		} catch (BusinessException be) {
			logger.info("MpcArchivosController:buscarEstadoArchivo:catch");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(),
					"Error de aplicaci\u00F3n");

			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());

			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("MpcArchivosController:buscarEstadoArchivo:numberHTTPDesired");

		}

		logger.info("MpcArchivosController:buscarEstadoArchivo:FinalReturn");
		return resultado;
	}

}
