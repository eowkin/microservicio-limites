package com.bancoexterior.parametros.limites.validator;

import org.springframework.validation.BindingResult;

import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestCrear;

public interface ILimitesValidator {

	public void validarConsulta(LimitesGeneralesRequestConsulta request);
	
	public void validarCrear(LimitesGeneralesRequestCrear request, BindingResult result);
	
	public void validarActualizar(LimitesGeneralesRequestCrear request, BindingResult result);
}
