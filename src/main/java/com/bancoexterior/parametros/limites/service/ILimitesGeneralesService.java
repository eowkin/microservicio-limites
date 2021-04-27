package com.bancoexterior.parametros.limites.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDto;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponse;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesDtoResponseActualizar;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestConsulta;
import com.bancoexterior.parametros.limites.dto.LimitesGeneralesRequestCrear;
import com.bancoexterior.parametros.limites.entities.LimitesGeneralesPk;

public interface ILimitesGeneralesService {

	public LimitesGeneralesDtoResponse consultaLimitesGenerales(LimitesGeneralesRequestConsulta request);
	
	public List<LimitesGeneralesDto> findAllDtoNuevo(LimitesGeneralesDtoConsulta limitesGeneralesDtoConsulta);
	
	public boolean existsById(LimitesGeneralesPk id);
	
	public LimitesGeneralesDto findById(LimitesGeneralesPk id);
	
	public LimitesGeneralesDtoResponseActualizar crear(LimitesGeneralesRequestCrear request, HttpServletRequest requestHTTP);
	
	public LimitesGeneralesDtoResponseActualizar actualizar(LimitesGeneralesRequestCrear request, HttpServletRequest requestHTTP);
}
