package com.bancoexterior.parametros.limites.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.bancoexterior.parametros.limites.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.limites.config.Codigos.Constantes;
import com.bancoexterior.parametros.limites.config.Codigos.Servicios;
import com.bancoexterior.parametros.limites.dto.DatosRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDto;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponse;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestCrear;
import com.bancoexterior.parametros.limites.entities.LimitesGenerales;
import com.bancoexterior.parametros.limites.exception.ParametroNoValid;
import com.bancoexterior.parametros.limites.response.ResponseBad;
import com.bancoexterior.parametros.limites.service.ILimitesGeneralesService;
import com.bancoexterior.parametros.limites.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@RequestMapping("/api/des/v1/parametros/limites")
@RequestMapping("${microservicio.path.pre}" + "${microservicio.ambiente}")
public class LimitesGeneralesController {

	@Autowired
	private ILimitesGeneralesService limitesService;
	
	@Autowired
	private Environment env;
	
	@GetMapping(path =Servicios.LIMITESGENERALESURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> listLimitesGeneralesResponse(@RequestBody LimitesGeneralesRequestConsulta limitesGeneralesRequestConsulta, 
			HttpServletRequest requestHTTP){
		log.info("[==== INICIO Convenio n° 1 LimitesGenerales - Controller ====]");
		log.info("limitesGeneralesRequestConsulta: " + limitesGeneralesRequestConsulta);
		LimitesGeneralesDtoResponse response;
		HttpStatus estatusCM;
		response = limitesService.consultaLimitesGenerales(limitesGeneralesRequestConsulta);
		estatusCM = Utils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		log.info("estatusCM: "+estatusCM);
		log.info("response: "+response);
		log.info("[==== FIN Convenio n° 1 LimitesGenerales - Controller ====]");
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
		log.info("[==== INICIO Convenio n° 1 LimitesGenerales - Controller ====]");
		log.info("limitesGeneralesRequestCrear: " + limitesGeneralesRequestCrear);
		
		//Validando datos de entrada
	    if (result.hasErrors()) {
			ResponseBad responseBad = new ResponseBad();	
			List<String> errors = result
	                .getFieldErrors()
	                .stream()
	                .map(FieldError::getDefaultMessage)
	                .collect(Collectors.toList());
	    	log.info("errors: "+errors);
			
			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			log.info("httpStatusError: "+httpStatusError);
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
			log.info("httpStatusError: "+httpStatusError);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1005);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1005,CodRespuesta.CDE1005));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
	    if(!tipoCliente.equals("N") && !tipoCliente.equals("J")) {
	    	ResponseBad responseBad = new ResponseBad();
	    	HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CDE1006);
			log.info("httpStatusError: "+httpStatusError);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CDE1006);
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CDE1006,CodRespuesta.CDE1006));
			return new ResponseEntity<>(responseBad, httpStatusError);
	    }
	    
		
		LimitesGeneralesDtoResponse response;
		HttpStatus estatusCM;
		/*
		response = limitesService.consultaLimitesGenerales(limitesGeneralesRequestConsulta);
		estatusCM = Utils.getHttpStatus(response.getResultado().getCodigo().trim());
		
		log.info("estatusCM: "+estatusCM);
		log.info("response: "+response);
		log.info("[==== FIN Convenio n° 1 LimitesGenerales - Controller ====]");
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(response,estatusCM);
		}else {
		
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}*/
		return null;
	}
	
	
	
	
	
	
	@GetMapping(path =Servicios.LIMITESGENERALESURLV1+"/todas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllLimitesGeneralesResponse(@Valid @RequestBody DatosRequestConsulta datosRequestConsulta, BindingResult result,
			HttpServletRequest requestHTTP){
		log.info("[==== INICIO Convenio n° 1 LimitesGenerales - Controller ====]");
		log.info("datosRequestConsulta: " + datosRequestConsulta);
		LimitesGeneralesDtoResponse response;
	
		if (result.hasErrors()) {
			
			ResponseBad responseBad = new ResponseBad();	
			List<String> errors = result
	                .getFieldErrors()
	                .stream()
	                .map(FieldError::getDefaultMessage)
	                .collect(Collectors.toList());
	    	log.info("errors: "+errors);
			
			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			log.info("httpStatusError: "+httpStatusError);
			responseBad.getResultadoBAD().setCodigo(errors.get(0));
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+errors.get(0),errors.get(0)));
			return new ResponseEntity(responseBad, httpStatusError);

		}
		response  = limitesService.findAllDtoResponse();
		log.info("[==== FIN Convenio n° 1 LimitesGenerales - Controller ====]");
		return ResponseEntity.ok(response);
	}
	
	//@GetMapping("/codMoneda/{codMoneda}/tipoTransaccion/{tipoTransaccion}/naturaleza/{naturaleza}")
	@GetMapping(path =Servicios.LIMITESGENERALESPARAMETERIDURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getIdLimitesGeneralesResponse(@Valid @RequestBody DatosRequestConsulta datosRequestConsulta, BindingResult result, @PathVariable String codMoneda,
			@PathVariable String tipoTransaccion,@PathVariable String naturaleza, 
			HttpServletRequest requestHTTP){
		log.info("[==== INICIO Convenio n° 1 LimitesGenerales - Controller ====]");
		log.info("datosRequestConsulta: " + datosRequestConsulta);
		log.info("codMoneda: "+codMoneda);
		log.info("tipoTransaccion: "+tipoTransaccion);
		log.info("naturaleza: "+naturaleza);
		if (result.hasErrors()) {
			
			ResponseBad responseBad = new ResponseBad();	
			List<String> errors = result
	                .getFieldErrors()
	                .stream()
	                .map(FieldError::getDefaultMessage)
	                .collect(Collectors.toList());
	    	log.info("errors: "+errors);
			
			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			log.info("httpStatusError: "+httpStatusError);
			responseBad.getResultadoBAD().setCodigo(errors.get(0));
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+errors.get(0),errors.get(0)));
			return new ResponseEntity(responseBad, httpStatusError);

		}
		LimitesGeneralesDtoResponse response  = limitesService.getLimitesGeneralesByParameter(codMoneda, tipoTransaccion, naturaleza);
		log.info("[==== FIN Convenio n° 1 LimitesGenerales - Controller ====]");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(path =Servicios.LIMITESGENERALESALLPARAMETERURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllParameterLimitesGeneralesResponse(@Valid @RequestBody DatosRequestConsulta datosRequestConsulta, BindingResult result, @PathVariable String codMoneda,
			@PathVariable String tipoTransaccion,@PathVariable String naturaleza, @PathVariable boolean flagActivo, 
			HttpServletRequest requestHTTP){
		log.info("[==== INICIO Convenio n° 1 LimitesGenerales - Controller ====]");
		log.info("datosRequestConsulta: " + datosRequestConsulta);
		log.info("codMoneda: "+codMoneda);
		log.info("tipoTransaccion: "+tipoTransaccion);
		log.info("naturaleza: "+naturaleza);
		log.info("flagActivo: "+flagActivo);
		if (result.hasErrors()) {
			
			ResponseBad responseBad = new ResponseBad();	
			List<String> errors = result
	                .getFieldErrors()
	                .stream()
	                .map(FieldError::getDefaultMessage)
	                .collect(Collectors.toList());
	    	log.info("errors: "+errors);
			
			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			log.info("httpStatusError: "+httpStatusError);
			responseBad.getResultadoBAD().setCodigo(errors.get(0));
	    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+errors.get(0),errors.get(0)));
			return new ResponseEntity(responseBad, httpStatusError);

		}
		LimitesGeneralesDtoResponse response  = limitesService.getLimitesGeneralesByAllParameter(codMoneda, tipoTransaccion, naturaleza, flagActivo);
		log.info("[==== FIN Convenio n° 1 LimitesGenerales - Controller ====]");
		return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping(path =Servicios.LIMITESGENERALESURLV1+"/prueba", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LimitesGenerales> findAll(){
		return limitesService.findAll();
	}
	
	@GetMapping(path =Servicios.LIMITESGENERALESURLV1+"/dto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LimitesGeneralesDto> findAllDto(){
		return limitesService.findAllDto();
	}
	
	
}
