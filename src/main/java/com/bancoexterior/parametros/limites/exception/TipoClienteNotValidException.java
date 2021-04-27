package com.bancoexterior.parametros.limites.exception;

public class TipoClienteNotValidException extends BadRequestUnprocessableException{

	public TipoClienteNotValidException(String codigo) {
		super(codigo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
