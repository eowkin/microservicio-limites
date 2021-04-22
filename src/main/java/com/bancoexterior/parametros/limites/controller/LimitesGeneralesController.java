package com.bancoexterior.parametros.limites.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bancoexterior.parametros.limites.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.limites.config.Codigos.Constantes;
import com.bancoexterior.parametros.limites.config.Codigos.Servicios;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponse;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponseActualizar;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestCrear;
import com.bancoexterior.parametros.limites.entities.LimitesGeneralesPk;
import com.bancoexterior.parametros.limites.response.ResponseBad;
import com.bancoexterior.parametros.limites.service.ILimitesGeneralesService;
import com.bancoexterior.parametros.limites.service.IMonedaService;
import com.bancoexterior.parametros.limites.util.Utils;



@RestController
@RequestMapping("${microservicio.path.pre}" + "${microservicio.ambiente}")
public class LimitesGeneralesController {

	private static final Logger LOGGER = LogManager.getLogger(LimitesGeneralesController.class);
	
	@Autowired
	private ILimitesGeneralesService limitesService;
	
	@Autowired
	private IMonedaService monedaService;
	
	@Autowired
	private Environment env;
	
	@PostMapping(path =Servicios.LIMITESGENERALESURLV1+"/consultas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> listLimitesGeneralesResponse(@RequestBody LimitesGeneralesRequestConsulta limitesGeneralesRequestConsulta, 
			HttpServletRequest requestHTTP){
		LOGGER.info(Servicios.LIMITESCONTROLLERI);
		LOGGER.info(limitesGeneralesRequestConsulta);
		String tipoTransaccion = limitesGeneralesRequestConsulta.getLimitesGeneralesDtoRequestConsulta().getTipoTransaccion();
	    String tipoCliente = limitesGeneralesRequestConsulta.getLimitesGeneralesDtoRequestConsulta().getNaturaleza();
		if(tipoTransaccion != null)
		    if(!tipoTransaccion.equals("C") && !tipoTransaccion.equals("V")) {
		    	ResponseBad responseBad = new ResponseBad();
		    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE1005);
				responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1005);
		    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1005,CodRespuesta.CDE1005));
				return new ResponseEntity<>(responseBad, httpStatusError);
		    }
	    
		if(tipoCliente != null)
		    if(!tipoCliente.equals("N") && !tipoCliente.equals("J")) {
		    	ResponseBad responseBad = new ResponseBad();
		    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE1006);
				responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1006);
		    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1006,CodRespuesta.CDE1006));
				return new ResponseEntity<>(responseBad, httpStatusError);
		    }
	    
	    
		LimitesGeneralesDtoResponse response;
		HttpStatus estatusCM;
		response = limitesService.consultaLimitesGenerales(limitesGeneralesRequestConsulta);
		estatusCM = Utils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		LOGGER.info(estatusCM);
		LOGGER.info(response);
		LOGGER.info(Servicios.LIMITESCONTROLLERF);
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(response,estatusCM);
		}else {
		
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
	}
	
	
	@PostMapping(path =Servicios.LIMITESGENERALESURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> crearLimitesGenerales(@Valid @RequestBody LimitesGeneralesRequestCrear limitesGeneralesRequestCrear, BindingResult result, 
			HttpServletRequest requestHTTP){
		LOGGER.info(Servicios.LIMITESCONTROLLERI);
		LOGGER.info(limitesGeneralesRequestCrear);
		
		//Validando datos de entrada
	    if (result.hasErrors()) {
			ResponseBad responseBad = new ResponseBad();	
			List<String> errors = result
	                .getFieldErrors()
	                .stream()
	                .map(FieldError::getDefaultMessage)
	                .collect(Collectors.toList());
			LOGGER.error(errors);
			
			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			responseBad.getResultadoBAD().setCodigo(errors.get(0));
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+errors.get(0),errors.get(0)));
			return new ResponseEntity<>(responseBad, httpStatusError);
			
		}
		
	    String codMoneda = limitesGeneralesRequestCrear.getLimitesGeneralesDtoRequestCrear().getCodMoneda();
	    String tipoTransaccion = limitesGeneralesRequestCrear.getLimitesGeneralesDtoRequestCrear().getTipoTransaccion();
	    String tipoCliente = limitesGeneralesRequestCrear.getLimitesGeneralesDtoRequestCrear().getNaturaleza();
	    
	    if(!tipoTransaccion.equals("C") && !tipoTransaccion.equals("V")) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE1005);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1005);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1005,CodRespuesta.CDE1005));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    if(!tipoCliente.equals("N") && !tipoCliente.equals("J")) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE1006);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1006);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1006,CodRespuesta.CDE1006));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    LimitesGeneralesPk id = new LimitesGeneralesPk();
	    id.setCodMoneda(codMoneda);
	    id.setTipoTransaccion(tipoTransaccion);
	    id.setNaturaleza(tipoCliente);
	    
	    if(limitesService.existsById(id)) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE2001);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE2001);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE2001,CodRespuesta.CDE2001));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    if(!monedaService.existsById(codMoneda)) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE2003);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE2003);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE2003,CodRespuesta.CDE2003));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    
		LimitesGeneralesDtoResponseActualizar response;
		HttpStatus estatusCM;
		
		response = limitesService.crear(limitesGeneralesRequestCrear, requestHTTP);
		estatusCM = Utils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		LOGGER.info(estatusCM);
		LOGGER.info(response);
		LOGGER.info(Servicios.LIMITESCONTROLLERF);
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(response,estatusCM);
		}else {
		
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
		
	}
	
	@PutMapping(path =Servicios.LIMITESGENERALESURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> actualizarLimitesGenerales(@Valid @RequestBody LimitesGeneralesRequestCrear limitesGeneralesRequestCrear, BindingResult result, 
			HttpServletRequest requestHTTP){
		LOGGER.info(Servicios.LIMITESCONTROLLERI);
		LOGGER.info(limitesGeneralesRequestCrear);
		
		//Validando datos de entrada
	    if (result.hasErrors()) {
			ResponseBad responseBad = new ResponseBad();	
			List<String> errors = result
	                .getFieldErrors()
	                .stream()
	                .map(FieldError::getDefaultMessage)
	                .collect(Collectors.toList());
			LOGGER.error(errors);
			
			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			responseBad.getResultadoBAD().setCodigo(errors.get(0));
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+errors.get(0),errors.get(0)));
			return new ResponseEntity<>(responseBad, httpStatusError);
			
		}
		
	    String codMoneda = limitesGeneralesRequestCrear.getLimitesGeneralesDtoRequestCrear().getCodMoneda();
	    String tipoTransaccion = limitesGeneralesRequestCrear.getLimitesGeneralesDtoRequestCrear().getTipoTransaccion();
	    String tipoCliente = limitesGeneralesRequestCrear.getLimitesGeneralesDtoRequestCrear().getNaturaleza();
	    
	    if(!tipoTransaccion.equals("C") && !tipoTransaccion.equals("V")) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE1005);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1005);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1005,CodRespuesta.CDE1005));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    if(!tipoCliente.equals("N") && !tipoCliente.equals("J")) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE1006);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1006);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1006,CodRespuesta.CDE1006));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    LimitesGeneralesPk id = new LimitesGeneralesPk();
	    id.setCodMoneda(codMoneda);
	    id.setTipoTransaccion(tipoTransaccion);
	    id.setNaturaleza(tipoCliente);
	    
	    if(!limitesService.existsById(id)) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE2000);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE2000);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE2000,CodRespuesta.CDE2000));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    
	    
	    
		LimitesGeneralesDtoResponseActualizar response;
		HttpStatus estatusCM;
		
		response = limitesService.actualizar(limitesGeneralesRequestCrear, requestHTTP);
		estatusCM = Utils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		LOGGER.info(estatusCM);
		LOGGER.info(response);
		LOGGER.info(Servicios.LIMITESCONTROLLERF);
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(response,estatusCM);
		}else {
		
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
		
	}
	
}
