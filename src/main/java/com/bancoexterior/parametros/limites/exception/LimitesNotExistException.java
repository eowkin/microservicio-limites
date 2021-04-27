package com.bancoexterior.parametros.limites.exception;

public class LimitesNotExistException extends BadRequestException{


	public LimitesNotExistException(String codigo) {
		super(codigo);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
