package com.bancoexterior.parametros.limites.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bancoexterior.parametros.limites.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.limites.config.Codigos.Constantes;
import com.bancoexterior.parametros.limites.config.Codigos.Servicios;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDto;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoRequestCrear;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponse;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponseActualizar;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestCrear;
import com.bancoexterior.parametros.limites.entities.LimitesGenerales;
import com.bancoexterior.parametros.limites.entities.LimitesGeneralesPk;
import com.bancoexterior.parametros.limites.repository.ILimitesGeneralesRepository;
import com.bancoexterior.parametros.limites.response.Resultado;
import com.bancoexterior.parametros.limites.interfase.IRegistrarAuditoriaService;
import com.bancoexterior.parametros.limites.model.RegistrarAuditoriaRequest;



@Service
public class LimitesGeneralesServiceImpl implements ILimitesGeneralesService{
	private static final Logger LOGGER = LogManager.getLogger(LimitesGeneralesServiceImpl.class);
	
	@Autowired
	private ILimitesGeneralesRepository repo;
	
	@Autowired
	private IRegistrarAuditoriaService registrarA;
	
	@Autowired
	private Environment env;
	

	
	

	@Override
	public List<LimitesGeneralesDto> findAllDto(LimitesGeneralesDtoConsulta limitesGeneralesDtoConsulta) {
		List<LimitesGeneralesDto> limitesGeneralesDto = null;
		
		
		//Todos los valores distintos de null
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByCodMonedaAndTipoTransaccionAndNaturalezaAndFlagActivo(limitesGeneralesDtoConsulta.getCodMoneda(), 
					limitesGeneralesDtoConsulta.getTipoTransaccion(), limitesGeneralesDtoConsulta.getNaturaleza(), limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//codMoneda
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getByCodMoneda(limitesGeneralesDtoConsulta.getCodMoneda());
		}
		
		//codMonedaAndtipoTransaccion
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getByCodMonedaAndTipoTransaccion(limitesGeneralesDtoConsulta.getCodMoneda(), limitesGeneralesDtoConsulta.getTipoTransaccion());
		}
		
		//codMonedaAndNaturaleza
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getByCodMonedaAndNaturaleza(limitesGeneralesDtoConsulta.getCodMoneda(), limitesGeneralesDtoConsulta.getNaturaleza());
		}
		
		//codMonedaAndFlagActivo
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByCodMonedaAndFlagActivo(limitesGeneralesDtoConsulta.getCodMoneda(), limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//codMonedaAndTipoTransaccionAndNaturaleza
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getById(limitesGeneralesDtoConsulta.getCodMoneda(), limitesGeneralesDtoConsulta.getTipoTransaccion(), limitesGeneralesDtoConsulta.getNaturaleza());
		}
		
		//codMOnedaAndNaturalezaAndFlagActivo
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByCodMonedaAndNaturalezaAndFlagActivo(limitesGeneralesDtoConsulta.getCodMoneda(), limitesGeneralesDtoConsulta.getNaturaleza(), limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//codMOnedaAndTipoTransaccionAndFlagActivo
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByCodMonedaAndTipoTransaccionAndFlagActivo(limitesGeneralesDtoConsulta.getCodMoneda(), limitesGeneralesDtoConsulta.getTipoTransaccion(), limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//tipoTransaccion
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getByTipoTrasaccion(limitesGeneralesDtoConsulta.getTipoTransaccion());
		}
		
		//tipoTransaccionAndNaturaleza
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getByTipoTrasaccionAndNaturaleza(limitesGeneralesDtoConsulta.getTipoTransaccion(), limitesGeneralesDtoConsulta.getNaturaleza());
		}
		
		//tipoTransaccionAndFlagActivo
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByTipoTransaccionAndFlagActivo(limitesGeneralesDtoConsulta.getTipoTransaccion(), limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//tipoTransaccionAndNaturalezAndFlagActivo
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() != null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByTipoTrasaccionAndNaturalezaAndFlagActivo(limitesGeneralesDtoConsulta.getTipoTransaccion(), limitesGeneralesDtoConsulta.getNaturaleza(), limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//naturaleza
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getByNaturaleza(limitesGeneralesDtoConsulta.getNaturaleza());
		}
		
		
		//naturalezaAndFlagActivo
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() != null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByNaturalezaAndFlagActivo(limitesGeneralesDtoConsulta.getNaturaleza(), limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//flagActivo
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
				&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			limitesGeneralesDto = repo.getByFlagActivo(limitesGeneralesDtoConsulta.getFlagActivo());
		}
		
		//all-Todos los valores null
		if (limitesGeneralesDtoConsulta.getCodMoneda() == null && limitesGeneralesDtoConsulta.getTipoTransaccion() == null 
			&& limitesGeneralesDtoConsulta.getNaturaleza() == null && limitesGeneralesDtoConsulta.getFlagActivo() == null) {
			limitesGeneralesDto = repo.getAll();
		}
		
		return limitesGeneralesDto;
	}
	
	
	@Override
	public LimitesGeneralesDtoResponse consultaLimitesGenerales(
			LimitesGeneralesRequestConsulta request) {
		LOGGER.info(Servicios.LIMITESSERVICEICONSULTAS);
		LimitesGeneralesDtoResponse response = new LimitesGeneralesDtoResponse();
		Resultado resultado = new Resultado();
		String codigo = CodRespuesta.C0000;
		String errorCM = Constantes.BLANK;
		List<LimitesGeneralesDto> listLimitesGeneralesDto;
		LimitesGeneralesDtoConsulta limitesGeneralesDtoConsulta = new LimitesGeneralesDtoConsulta(request);
		
		try {
			
			codigo = validaDatosConsulta(request);
			LOGGER.info(codigo);
			if(codigo.equalsIgnoreCase(CodRespuesta.C0000)) {
				
				//consulta BD
				listLimitesGeneralesDto = this.findAllDto(limitesGeneralesDtoConsulta);
				response.setListLimitesGeneralesDto(listLimitesGeneralesDto);
				
				//Validar Respuesta
				resultado = validaConsulta(listLimitesGeneralesDto);
				codigo = resultado.getCodigo();
				errorCM = resultado.getDescripcion();
			}
			
			
		} catch (Exception e) {
			LOGGER.error(e);
			codigo = CodRespuesta.CME6000;
			errorCM = Constantes.EXC+e;
		}
		response.getResultado().setCodigo(codigo);
		response.getResultado().setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorCM));
		
		LOGGER.info(response);
		LOGGER.info(Servicios.LIMITESSERVICEFCONSULTAS);
		return response;
	}
	
	private String validaDatosConsulta(LimitesGeneralesRequestConsulta request) {
		LOGGER.info("dentro de validarDatosConsulta");
		LOGGER.info(request);
		String codigo = CodRespuesta.C0000;
		String codMoneda;
		String tipoTransaccion;
		String naturaleza;
		boolean flagActivo;
		
		
		codMoneda = request.getLimitesGeneralesDtoRequestConsulta().getCodMoneda() == null ? "000":request.getLimitesGeneralesDtoRequestConsulta().getCodMoneda();
		tipoTransaccion = request.getLimitesGeneralesDtoRequestConsulta().getTipoTransaccion() == null ? "000":request.getLimitesGeneralesDtoRequestConsulta().getTipoTransaccion();
		naturaleza = request.getLimitesGeneralesDtoRequestConsulta().getNaturaleza() == null ? "000":request.getLimitesGeneralesDtoRequestConsulta().getNaturaleza();
		flagActivo = request.getLimitesGeneralesDtoRequestConsulta().getFlagActivo() == null ? Boolean.parseBoolean(Constantes.TRUE) : request.getLimitesGeneralesDtoRequestConsulta().getFlagActivo();
		
		
		request.getLimitesGeneralesDtoRequestConsulta().setCodMoneda(codMoneda);
		request.getLimitesGeneralesDtoRequestConsulta().setTipoTransaccion(tipoTransaccion);
		request.getLimitesGeneralesDtoRequestConsulta().setNaturaleza(naturaleza);
		request.getLimitesGeneralesDtoRequestConsulta().setFlagActivo(flagActivo);
		
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<LimitesGeneralesRequestConsulta>> errores = validator.validate(request);
		
	
			for (ConstraintViolation<LimitesGeneralesRequestConsulta> cv : errores) {
				
				if ( !cv.getMessage().equalsIgnoreCase(Constantes.BLANK)) {
					codigo = cv.getMessage();
					 break;
				}

			}

		
		return codigo;
	}

	
	
	/**
     * Nombre:                  validaConsulta
     * Descripcion:             Metodo para evaluar el resultado de la consulta de las monedas
     *
     * @param  Objeto List<MonedasBD>
     * @return Resultado  Objeto con la información de la evaluacion.
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 16/03/21
    */ 
	
	private Resultado validaConsulta(List<LimitesGeneralesDto> listLimitesGeneralesDto) {
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(Constantes.BLANK);
		
		if(listLimitesGeneralesDto.isEmpty()) {
			resultado.setCodigo(CodRespuesta.C0001);
			return resultado;
		}

		/*
	    if(monedasBD.get(0).getCodMonedaBD().equalsIgnoreCase(Constantes.SERROR)) {
	    	resultado.setCodigo(CodRespuesta.CME6002);
	    	resultado.setDescripcion(monedasBD.get(0).getDescripcionBD());
	    	 LOGGER.error(resultado);
	    	return resultado;
	    }*/

	    
		LOGGER.info(resultado);
		return resultado;
		
	}
	
	/**
     * Nombre:                 registrarAuditoriaBD
     * Descripcion:            Registrar Auditoria en Web Service
     *
     * @param  req  Objeto RegistrarAuditoriaRequest
     * @param  codigo   Codigo de respuesta
     * @param descripcion Descripcion del resultado
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 02/03/21
     */
	private void registrarAuditoriaBD(RegistrarAuditoriaRequest registrarAu,Resultado response, String errorAdicional) {
			
		        registrarA.registrarAuditoria(registrarAu, response.getCodigo(),response.getDescripcion(),errorAdicional);	
	}

	@Override
	public boolean existsById(LimitesGeneralesPk id) {
		return repo.existsById(id);
	}

	@Override
	public LimitesGeneralesDtoResponseActualizar crear(LimitesGeneralesRequestCrear request, HttpServletRequest requestHTTP) {
		LOGGER.info(Servicios.LIMITESSERVICEICREAR);
		LOGGER.info(request);
		String microservicio = Servicios.LIMITES;
		
		RegistrarAuditoriaRequest reAU = null;
		
		reAU = new RegistrarAuditoriaRequest(request, microservicio, requestHTTP);
		String errorM = Constantes.BLANK;
		String codigo =  CodRespuesta.C0000;
		
		LimitesGenerales obj = new LimitesGenerales();
		
		LimitesGeneralesDtoResponseActualizar response = new LimitesGeneralesDtoResponseActualizar();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		
		try {
			
			LimitesGeneralesDtoRequestCrear dtoRequestCrear = request.getLimitesGeneralesDtoRequestCrear();
			LimitesGeneralesPk id = new LimitesGeneralesPk();
			id.setCodMoneda(dtoRequestCrear.getCodMoneda());
			id.setTipoTransaccion(dtoRequestCrear.getTipoTransaccion());
			id.setNaturaleza(dtoRequestCrear.getNaturaleza());
			obj.setId(id);
			obj.setMontoMin(dtoRequestCrear.getMontoMin());
			obj.setMontoMax(dtoRequestCrear.getMontoMax());
			obj.setMontoTope(dtoRequestCrear.getMontoTope());
			obj.setMontoMensual(dtoRequestCrear.getMontoMensual());
			obj.setMontoDiario(dtoRequestCrear.getMontoDiario());
			obj.setMontoBanco(dtoRequestCrear.getMontoBanco());
			obj.setCodUsuario(request.getCodUsuarioMR());
			obj.setFlagActivo(dtoRequestCrear.getFlagActivo());
			
			LOGGER.info(obj);
			obj = repo.save(obj);
			response.setResultado(resultado);
			
		} catch (Exception e) {
			LOGGER.error(e);
			codigo = CodRespuesta.CME6001;
			errorM = Constantes.EXC+e;
			response.getResultado().setCodigo(CodRespuesta.CME6001);
			response.getResultado().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
			
		}
		resultado.setCodigo(codigo);
		resultado.setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorM));
		
		if(reAU != null) {
			reAU.setIdCliente(Constantes.RIF);
			reAU.setCedula(Constantes.CEDULA);
			reAU.setTelefono(Constantes.TELEFONO);
			reAU.setIdCanal(request.getCanalCM());
			registrarAuditoriaBD(reAU, resultado, errorM);
		}
		
		LOGGER.info(Servicios.LIMITESSERVICEFCREAR);
		return response;
		
	}


	@Override
	public LimitesGeneralesDtoResponseActualizar actualizar(LimitesGeneralesRequestCrear request,
			HttpServletRequest requestHTTP) {
		LOGGER.info(Servicios.LIMITESSERVICEIACTUALIZAR);
		LOGGER.info(request);
		String microservicio = Servicios.LIMITESACTUALIZAR;
		
		RegistrarAuditoriaRequest reAU = null;
		
		reAU = new RegistrarAuditoriaRequest(request, microservicio, requestHTTP);
		String errorM = Constantes.BLANK;
		String codigo =  CodRespuesta.C0000;
		
		LimitesGenerales obj = new LimitesGenerales();
		
		LimitesGeneralesDtoResponseActualizar response = new LimitesGeneralesDtoResponseActualizar();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		
		try {
			
			LimitesGeneralesDtoRequestCrear dtoRequestCrear = request.getLimitesGeneralesDtoRequestCrear();
			
			LimitesGeneralesPk id = new LimitesGeneralesPk();
			id.setCodMoneda(dtoRequestCrear.getCodMoneda());
			id.setTipoTransaccion(dtoRequestCrear.getTipoTransaccion());
			id.setNaturaleza(dtoRequestCrear.getNaturaleza());
			
			LimitesGeneralesDto limitesGeneralesDto = this.findById(id);
			
			obj.setId(id);
			obj.setMontoMin(dtoRequestCrear.getMontoMin());
			obj.setMontoMax(dtoRequestCrear.getMontoMax());
			obj.setMontoTope(dtoRequestCrear.getMontoTope());
			obj.setMontoMensual(dtoRequestCrear.getMontoMensual());
			obj.setMontoDiario(dtoRequestCrear.getMontoDiario());
			obj.setMontoBanco(dtoRequestCrear.getMontoBanco());
			obj.setCodUsuario(request.getCodUsuarioMR());
			obj.setFlagActivo(dtoRequestCrear.getFlagActivo());
			obj.setFechaModificacion(limitesGeneralesDto.getFechaModificacion());
			
			LOGGER.info(obj);
			obj = repo.save(obj);
			response.setResultado(resultado);
			
		} catch (Exception e) {
			LOGGER.error(e);
			codigo = CodRespuesta.CME6001;
			errorM = Constantes.EXC+e;
			response.getResultado().setCodigo(CodRespuesta.CME6001);
			response.getResultado().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
			
		}
		resultado.setCodigo(codigo);
		resultado.setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorM));
		
		if(reAU != null) {
			reAU.setIdCliente(Constantes.RIF);
			reAU.setCedula(Constantes.CEDULA);
			reAU.setTelefono(Constantes.TELEFONO);
			reAU.setIdCanal(request.getCanalCM());
			registrarAuditoriaBD(reAU, resultado, errorM);
		}
		
		LOGGER.info(Servicios.LIMITESSERVICEFACTUALIZAR);
		return response;
	}


	@Override
	public LimitesGeneralesDto findById(LimitesGeneralesPk id) {
		LimitesGenerales limitesGenerales =  repo.findById(id).orElse(null);
		
		if(limitesGenerales != null) {
			LimitesGeneralesDto limitesGeneralesDto = new LimitesGeneralesDto();
			limitesGeneralesDto.setCodMoneda(limitesGenerales.getId().getCodMoneda());
			limitesGeneralesDto.setTipoTransaccion(limitesGenerales.getId().getTipoTransaccion());
			limitesGeneralesDto.setNaturaleza(limitesGenerales.getId().getNaturaleza());
			limitesGeneralesDto.setMontoMin(limitesGenerales.getMontoMin());
			limitesGeneralesDto.setMontoMax(limitesGenerales.getMontoMax());
			limitesGeneralesDto.setMontoTope(limitesGenerales.getMontoTope());
			limitesGeneralesDto.setMontoMensual(limitesGenerales.getMontoMensual());
			limitesGeneralesDto.setMontoDiario(limitesGenerales.getMontoDiario());
			limitesGeneralesDto.setMontoBanco(limitesGenerales.getMontoBanco());
			limitesGeneralesDto.setFlagActivo(limitesGenerales.getFlagActivo());
			limitesGeneralesDto.setFechaModificacion(limitesGenerales.getFechaModificacion());
			return limitesGeneralesDto;
		}else {
			return null;
		}
		
		
	}

	

}
