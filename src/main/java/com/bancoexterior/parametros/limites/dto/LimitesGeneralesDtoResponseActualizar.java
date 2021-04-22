package com.bancoexterior.parametros.limites.dto;

import java.io.Serializable;

import com.bancoexterior.parametros.limites.response.Resultado;

import lombok.Data;

@Data
public class LimitesGeneralesDtoResponseActualizar implements Serializable{
	

	
	private Resultado resultado;
	
	public LimitesGeneralesDtoResponseActualizar() {
		super();
		this.resultado = new Resultado();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
