package com.bancoexterior.parametros.limites.service;

import java.util.ArrayList;
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
	

	
	

	/**
	 * Nombre: findAllDtoNuevo 
	 * Descripcion: Invocar metodo para una busqueda de los limites con
	 * los parametros enviados.
	 *
	 * @param limitesGeneralesDtoConsulta     Objeto tipo LimitesGeneralesDtoConsulta 
	 * @return List<LimitesGeneralesDto>
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	
	@Override
	public List<LimitesGeneralesDto> findAllDtoNuevo(LimitesGeneralesDtoConsulta limitesGeneralesDtoConsulta) {
		
		
		String codMoneda = Constantes.BLANK;
		String tipoTransaccion = Constantes.BLANK;
		String tipoCliente = Constantes.BLANK;
		String flag = Constantes.BLANK;
		boolean flagActivo = false;
		
		if (limitesGeneralesDtoConsulta.getCodMoneda() != null) {
			codMoneda = limitesGeneralesDtoConsulta.getCodMoneda();
		}
		
		if (limitesGeneralesDtoConsulta.getTipoTransaccion() != null) {
			tipoTransaccion = limitesGeneralesDtoConsulta.getTipoTransaccion();
		}
		
		if (limitesGeneralesDtoConsulta.getTipoCliente() != null) {
			tipoCliente = limitesGeneralesDtoConsulta.getTipoCliente();
		}
		
		if (limitesGeneralesDtoConsulta.getFlagActivo() != null) {
			flag = "si";
			flagActivo = limitesGeneralesDtoConsulta.getFlagActivo();
		}
		
		
		List<LimitesGenerales> listLimites = repo.getLimitesByNuevo(codMoneda, tipoTransaccion, tipoCliente, flag, flagActivo);
		List<LimitesGeneralesDto> listLimitesGeneralesDto = new ArrayList<LimitesGeneralesDto>();
		
		for (LimitesGenerales limitesGenerales : listLimites) {
			LimitesGeneralesDto limitesGeneralesDto = new LimitesGeneralesDto();
			limitesGeneralesDto.setCodMoneda(limitesGenerales.getId().getCodMoneda());
			limitesGeneralesDto.setTipoTransaccion(limitesGenerales.getId().getTipoTransaccion());
			limitesGeneralesDto.setTipoCliente(limitesGenerales.getId().getTipoCliente());
			limitesGeneralesDto.setMontoMin(limitesGenerales.getMontoMin());
			limitesGeneralesDto.setMontoMax(limitesGenerales.getMontoMax());
			limitesGeneralesDto.setMontoTope(limitesGenerales.getMontoTope());
			limitesGeneralesDto.setMontoMensual(limitesGenerales.getMontoMensual());
			limitesGeneralesDto.setMontoDiario(limitesGenerales.getMontoDiario());
			limitesGeneralesDto.setMontoBanco(limitesGenerales.getMontoBanco());
			limitesGeneralesDto.setCodUsuario(limitesGenerales.getCodUsuario());
			limitesGeneralesDto.setFlagActivo(limitesGenerales.getFlagActivo());
			limitesGeneralesDto.setFechaModificacion(limitesGenerales.getFechaModificacion());
			listLimitesGeneralesDto.add(limitesGeneralesDto);
		}
		
		return listLimitesGeneralesDto;
	}
	
	/**
	 * Nombre: consultaLimitesGenerales 
	 * Descripcion: Invocar metodo para la gestion de consulta a realizar
	 * para la busqueda de los limites con los parametros enviados.
	 *
	 * @param request     Objeto tipo LimitesGeneralesRequestConsulta
	 * @return MonedaDtoResponse
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
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
				//listLimitesGeneralesDto = this.findAllDto(limitesGeneralesDtoConsulta);
				listLimitesGeneralesDto = this.findAllDtoNuevo(limitesGeneralesDtoConsulta);
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
	
	
	/**
     * Nombre:                  validaDatosConsulta
     * Descripcion:             Valida datos de entrada del metodo de consulta.
     *
     * @param  Objeto MonedasRequest
     * @return String  Codigo resultado de la evaluacion.
     * @version 1.0
     * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	
	private String validaDatosConsulta(LimitesGeneralesRequestConsulta request) {
		LOGGER.info("dentro de validarDatosConsulta");
		LOGGER.info(request);
		String codigo = CodRespuesta.C0000;
		String codMoneda;
		String tipoTransaccion;
		String tipoCliente;
		boolean flagActivo;
		
		
		codMoneda = request.getLimitesGeneralesDtoRequestConsulta().getCodMoneda() == null ? "000":request.getLimitesGeneralesDtoRequestConsulta().getCodMoneda();
		tipoTransaccion = request.getLimitesGeneralesDtoRequestConsulta().getTipoTransaccion() == null ? "000":request.getLimitesGeneralesDtoRequestConsulta().getTipoTransaccion();
		tipoCliente = request.getLimitesGeneralesDtoRequestConsulta().getTipoCliente() == null ? "000":request.getLimitesGeneralesDtoRequestConsulta().getTipoCliente();
		flagActivo = request.getLimitesGeneralesDtoRequestConsulta().getFlagActivo() == null ? Boolean.parseBoolean(Constantes.TRUE) : request.getLimitesGeneralesDtoRequestConsulta().getFlagActivo();
		
		
		request.getLimitesGeneralesDtoRequestConsulta().setCodMoneda(codMoneda);
		request.getLimitesGeneralesDtoRequestConsulta().setTipoTransaccion(tipoTransaccion);
		request.getLimitesGeneralesDtoRequestConsulta().setTipoCliente(tipoCliente);
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
     * @param listLimitesGeneralesDto   Objeto List<LimitesGeneralesDto> 
     * @return Resultado                Objeto con la información de la evaluacion.
     * @version 1.0
     * @author Eugenio Owkin
	 * @since 12/04/21
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
     * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	private void registrarAuditoriaBD(RegistrarAuditoriaRequest registrarAu,Resultado response, String errorAdicional) {
			
		        registrarA.registrarAuditoria(registrarAu, response.getCodigo(),response.getDescripcion(),errorAdicional);	
	}

	
	/**
	 * Nombre: existsById 
	 * Descripcion: Invocar metodo para buscar si existe o no 
	 * un limite por id.
	 * @param id LimitesGeneralesPk   
	 * @return boolean
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	@Override
	public boolean existsById(LimitesGeneralesPk id) {
		return repo.existsById(id);
	}

	
	/**
	 * Nombre: crear 
	 * Descripcion: Invocar metodo para crear el limite con
	 * los parametros enviados.
	 *
	 * @param request     Objeto tipo LimitesGeneralesRequestCrear
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return LimitesGeneralesDtoResponseActualizar
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	
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
			id.setTipoCliente(dtoRequestCrear.getTipoCliente());
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

	/**
	 * Nombre: actualizar 
	 * Descripcion: Invocar metodo para actualizar la monedas con
	 * los parametros enviados.
	 *
	 * @param request     Objeto tipo LimitesGeneralesRequestCrear
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return LimitesGeneralesDtoResponseActualizar
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	@Override
	public LimitesGeneralesDtoResponseActualizar actualizar(LimitesGeneralesRequestCrear request,
			HttpServletRequest requestHTTP) {
		LOGGER.info(Servicios.LIMITESSERVICEIACTUALIZAR);
		//LOGGER.info(request);
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
			id.setTipoCliente(dtoRequestCrear.getTipoCliente());
			
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
			
			//LOGGER.info(obj);
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


	
	/**
	 * Nombre: findById 
	 * Descripcion: Invocar metodo para una busqueda de un limite
	 * por id.
	 *
	 * @param id LimitesGeneralesPk   
	 * @return LimitesGeneralesDto
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	@Override
	public LimitesGeneralesDto findById(LimitesGeneralesPk id) {
		LimitesGenerales limitesGenerales =  repo.findById(id).orElse(null);
		
		if(limitesGenerales != null) {
			LimitesGeneralesDto limitesGeneralesDto = new LimitesGeneralesDto();
			limitesGeneralesDto.setCodMoneda(limitesGenerales.getId().getCodMoneda());
			limitesGeneralesDto.setTipoTransaccion(limitesGenerales.getId().getTipoTransaccion());
			limitesGeneralesDto.setTipoCliente(limitesGenerales.getId().getTipoCliente());
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
