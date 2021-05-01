package com.bancoexterior.parametros.limites.controller;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bancoexterior.parametros.limites.config.Codigos.Constantes;
import com.bancoexterior.parametros.limites.config.Codigos.Servicios;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponse;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponseActualizar;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestCrear;
import com.bancoexterior.parametros.limites.service.ILimitesGeneralesService;
import com.bancoexterior.parametros.limites.util.LibreriaUtils;
import com.bancoexterior.parametros.limites.validator.ILimitesValidator;




@RestController
@RequestMapping("${microservicio.path.pre}" + "${microservicio.ambiente}")
public class LimitesGeneralesController {

	private static final Logger LOGGER = LogManager.getLogger(LimitesGeneralesController.class);
	
	@Autowired
	private ILimitesGeneralesService limitesService;
	
	
	@Autowired
	private ILimitesValidator limitesValidator;
	
	
	/**
	 * Nombre: listLimitesGeneralesResponse 
	 * Descripcion: Invocar metodo para consultar limites parametros
	 *
	 * @param limitesGeneralesRequestConsulta     Objeto tipo LimitesGeneralesRequestConsulta
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return ResponseEntity<Object>
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	@PostMapping(path =Servicios.LIMITESGENERALESURLV1+"/consultas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> listLimitesGeneralesResponse(@RequestBody LimitesGeneralesRequestConsulta limitesGeneralesRequestConsulta, 
			HttpServletRequest requestHTTP){
		LOGGER.info(Servicios.LIMITESCONTROLLERI);
		LOGGER.info(limitesGeneralesRequestConsulta);
		
		limitesValidator.validarConsulta(limitesGeneralesRequestConsulta);
		
		LimitesGeneralesDtoResponse response;
		HttpStatus estatusCM;
		response = limitesService.consultaLimitesGenerales(limitesGeneralesRequestConsulta);
		estatusCM = LibreriaUtils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		LOGGER.info(estatusCM);
		LOGGER.info(response);
		LOGGER.info(Servicios.LIMITESCONTROLLERF);
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(response,estatusCM);
		}else {
		
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
	}
	
	/**
	 * Nombre: crearLimitesGenerales 
	 * Descripcion: Invocar metodo para ingresar limite nuevo
	 * 
	 * @param limitesGeneralesRequestCrear     Objeto tipo LimitesGeneralesRequestCrear 
	 * @param result Objeto tipo BindingResult 
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return ResponseEntity<Object>
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @throws ApiUnprocessableEntity 
	 * @since 12/04/21
	 */

	@PostMapping(path =Servicios.LIMITESGENERALESURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> crearLimitesGenerales(@Valid @RequestBody LimitesGeneralesRequestCrear limitesGeneralesRequestCrear, BindingResult result, 
			HttpServletRequest requestHTTP){
		LOGGER.info(Servicios.LIMITESCONTROLLERI);
		LOGGER.info(limitesGeneralesRequestCrear);
		
		limitesValidator.validarCrear(limitesGeneralesRequestCrear, result);
	    
	    
		LimitesGeneralesDtoResponseActualizar response;
		HttpStatus estatusCM;
		
		response = limitesService.crear(limitesGeneralesRequestCrear, requestHTTP);
		estatusCM = LibreriaUtils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		LOGGER.info(estatusCM);
		LOGGER.info(response);
		LOGGER.info(Servicios.LIMITESCONTROLLERF);
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(response,estatusCM);
		}else {
		
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
		
	}
	
	
	/**
	 * Nombre: actualizarLimitesGenerales 
	 * Descripcion: Invocar metodo para actualizar limite nuevo
	 * 
	 * @param limitesGeneralesRequestCrear     Objeto tipo LimitesGeneralesRequestCrear 
	 * @param result Objeto tipo BindingResult 
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return ResponseEntity<Object>
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @throws ApiUnprocessableEntity 
	 * @since 12/04/21
	 */
	@PutMapping(path =Servicios.LIMITESGENERALESURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> actualizarLimitesGenerales(@Valid @RequestBody LimitesGeneralesRequestCrear limitesGeneralesRequestCrear, BindingResult result, 
			HttpServletRequest requestHTTP){
		LOGGER.info(Servicios.LIMITESCONTROLLERI);
		LOGGER.info(limitesGeneralesRequestCrear);
		
		limitesValidator.validarActualizar(limitesGeneralesRequestCrear, result);
		
		LimitesGeneralesDtoResponseActualizar response;
		HttpStatus estatusCM;
		
		response = limitesService.actualizar(limitesGeneralesRequestCrear, requestHTTP);
		estatusCM = LibreriaUtils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		
		LOGGER.info(response);
		LOGGER.info(Servicios.LIMITESCONTROLLERF);
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(response,estatusCM);
		}else {
		
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
		
	}
	
}
