package com.bancoexterior.parametros.limites.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bancoexterior.parametros.limites.response.Resultado;

import lombok.Data;


@Data
public class LimitesGeneralesDtoResponse implements Serializable{

	
	private Resultado resultado;
	
	private List<LimitesGeneralesDto> listLimitesGeneralesDto;
	
	
	
	
	public LimitesGeneralesDtoResponse() {
		super();
		this.resultado = new Resultado();
		this.listLimitesGeneralesDto = new ArrayList<LimitesGeneralesDto>(); 
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
