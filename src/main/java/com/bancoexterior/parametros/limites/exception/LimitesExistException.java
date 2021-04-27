package com.bancoexterior.parametros.limites.exception;

public class LimitesExistException extends BadRequestException{


	public LimitesExistException(String codigo) {
		super(codigo);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
