package com.bancoexterior.parametros.limites.exception;

public class TipoTransaccionNotValidException extends BadRequestUnprocessableException{

	public TipoTransaccionNotValidException(String codigo) {
		super(codigo);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
