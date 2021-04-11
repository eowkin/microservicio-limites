package com.bancoexterior.parametros.limites.service;

import java.util.List;

import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDto;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponse;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.entities.LimitesGenerales;

public interface ILimitesGeneralesService {

	public LimitesGeneralesDtoResponse consultaLimitesGenerales(LimitesGeneralesRequestConsulta request);
	
	public List<LimitesGenerales> findAll();
	
	public List<LimitesGeneralesDto> findAllDto();
	
	public List<LimitesGeneralesDto> findAllDto(LimitesGeneralesDtoConsulta limitesGeneralesDtoConsulta);
	
	public LimitesGeneralesDtoResponse findAllDtoResponse();
	
	public LimitesGeneralesDtoResponse getLimitesGeneralesByParameter(String codMoneda, String tipoTransaccion, String naturaleza);
	
	public LimitesGeneralesDtoResponse getLimitesGeneralesByAllParameter(String codMoneda, String tipoTransaccion, String naturaleza, boolean flagActivo);
}
