package com.bancoexterior.parametros.limites.dto;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LimitesGeneralesDto implements Serializable{

	@JsonProperty("codMoneda")
	private String codMoneda;
	
	@JsonProperty("tipoTransaccion")
	private String tipoTransaccion;
	
	@JsonProperty("naturaleza")
	private String naturaleza;
	
	@JsonProperty("montoMin")
	private Double montoMin;
	
	@JsonProperty("montoMax")
	private Double montoMax;
	
	@JsonProperty("montoTope")
	private Double montoTope;
	
	@JsonProperty("montoMensual")
	private Double montoMensual;
	
	@JsonProperty("montoDiario")
	private Double montoDiario;
	
	@JsonProperty("montoBanco")
	private Double montoBanco;
	
	@JsonProperty("codUsuario")
	private String codUsuario;
	
	@JsonProperty("flagActivo")
	private Boolean flagActivo;
	
	@JsonProperty("fechaModificacion")
	private Date fechaModificacion;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
