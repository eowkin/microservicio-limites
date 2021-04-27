package com.bancoexterior.parametros.limites.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.bancoexterior.parametros.limites.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestCrear;
import com.bancoexterior.parametros.limites.entities.LimitesGeneralesPk;
import com.bancoexterior.parametros.limites.exception.TipoClienteNotValidException;
import com.bancoexterior.parametros.limites.exception.TipoTransaccionNotValidException;
import com.bancoexterior.parametros.limites.service.ILimitesGeneralesService;
import com.bancoexterior.parametros.limites.service.IMonedaService;
import com.bancoexterior.parametros.limites.exception.CodMonedaNoExistException;
import com.bancoexterior.parametros.limites.exception.FieldErrorValidationException;
import com.bancoexterior.parametros.limites.exception.LimitesExistException;
import com.bancoexterior.parametros.limites.exception.LimitesNotExistException;


@Component
public class LimitesValidatorImpl implements ILimitesValidator{

	@Autowired
	private ILimitesGeneralesService limitesService;
	
	@Autowired
	private IMonedaService monedaService;
	
	/**
	 * Nombre: validarConsulta 
	 * Descripcion: Invocar metodo para realizar validacion
	 * de los parametros de entrada y demas validaciones 
	 * antes de procesar al endPoint consultar limites.
	 *
	 * @param request     Objeto tipo LimitesGeneralesRequestConsulta
	 * @return void
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	
	@Override
	public void validarConsulta(LimitesGeneralesRequestConsulta request) {
		String tipoTransaccion = request.getLimitesGeneralesDtoRequestConsulta().getTipoTransaccion();
		String tipoCliente = request.getLimitesGeneralesDtoRequestConsulta().getTipoCliente();
		if(tipoTransaccion != null)
			if(!tipoTransaccion.equals("C") && !tipoTransaccion.equals("V")) {
				throw new TipoTransaccionNotValidException(CodRespuesta.CDE1005);
			}
		
		
		if(tipoCliente != null)
		    if(!tipoCliente.equals("N") && !tipoCliente.equals("J")) {
		    	throw new TipoClienteNotValidException(CodRespuesta.CDE1006);
		    }
		
		
	}

	@Override
	public void validarCrear(LimitesGeneralesRequestCrear request, BindingResult result) {
		//Validando los valores de entrada
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			throw new FieldErrorValidationException(errors.get(0));			
		}
		
		String codMoneda = request.getLimitesGeneralesDtoRequestCrear().getCodMoneda();
		String tipoTransaccion = request.getLimitesGeneralesDtoRequestCrear().getTipoTransaccion();
		String tipoCliente = request.getLimitesGeneralesDtoRequestCrear().getTipoCliente();
		
		if(!tipoTransaccion.equals("C") && !tipoTransaccion.equals("V")) {
			throw new TipoTransaccionNotValidException(CodRespuesta.CDE1005);
		}
		
		
	    if(!tipoCliente.equals("N") && !tipoCliente.equals("J")) {
		   	throw new TipoClienteNotValidException(CodRespuesta.CDE1006);
	    }
	    
	    LimitesGeneralesPk id = new LimitesGeneralesPk();
	    id.setCodMoneda(codMoneda);
	    id.setTipoTransaccion(tipoTransaccion);
	    id.setTipoCliente(tipoCliente);
	    
	    if(limitesService.existsById(id)) {
	    	throw new LimitesExistException(CodRespuesta.CDE2001);
	    }
		
	    if(!monedaService.existsById(codMoneda)) {
	    	throw new CodMonedaNoExistException(CodRespuesta.CDE2003);
	    }
	}

	@Override
	public void validarActualizar(LimitesGeneralesRequestCrear request, BindingResult result) {
		//Validando los valores de entrada
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			throw new FieldErrorValidationException(errors.get(0));			
		}
		
		String codMoneda = request.getLimitesGeneralesDtoRequestCrear().getCodMoneda();
		String tipoTransaccion = request.getLimitesGeneralesDtoRequestCrear().getTipoTransaccion();
		String tipoCliente = request.getLimitesGeneralesDtoRequestCrear().getTipoCliente();
		
		if(!tipoTransaccion.equals("C") && !tipoTransaccion.equals("V")) {
			throw new TipoTransaccionNotValidException(CodRespuesta.CDE1005);
		}
		
		
	    if(!tipoCliente.equals("N") && !tipoCliente.equals("J")) {
		   	throw new TipoClienteNotValidException(CodRespuesta.CDE1006);
	    }
	    
	    LimitesGeneralesPk id = new LimitesGeneralesPk();
	    id.setCodMoneda(codMoneda);
	    id.setTipoTransaccion(tipoTransaccion);
	    id.setTipoCliente(tipoCliente);
	    
	    if(!limitesService.existsById(id)) {
	    	throw new LimitesNotExistException(CodRespuesta.CDE2000);
	    }
	}

}
