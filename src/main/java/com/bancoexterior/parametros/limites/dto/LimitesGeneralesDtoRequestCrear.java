package com.bancoexterior.parametros.limites.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bancoexterior.parametros.limites.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.limites.config.Codigos.ParamConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class LimitesGeneralesDtoRequestCrear implements Serializable{
	
	

	@JsonProperty("codMoneda")
	@NotEmpty(message=CodRespuesta.CDE1004)
	@Pattern(regexp=ParamConfig.CODMONEDA, message=CodRespuesta.CDE1004)
	private String codMoneda;
	
	@JsonProperty("tipoTransaccion")
	@Size(min = 1, max = 1, message=CodRespuesta.CDE1005)
	@NotEmpty(message=CodRespuesta.CDE1005)
	private String tipoTransaccion;
	
	@JsonProperty("tipoCliente")
	@Size(min = 1, max = 1, message=CodRespuesta.CDE1006)
	@NotEmpty(message=CodRespuesta.CDE1006)
	private String tipoCliente;
	
	
	@JsonProperty("montoMin")
	@NotNull(message = CodRespuesta.CDE1009)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1009)
	private BigDecimal montoMin;
	
	@JsonProperty("montoMax")
	@NotNull(message = CodRespuesta.CDE1010)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1010)
	private BigDecimal montoMax;
	
	@JsonProperty("montoTope")
	@NotNull(message = CodRespuesta.CDE1011)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1011)
	private BigDecimal montoTope;
	
	@JsonProperty("montoMensual")
	@NotNull(message = CodRespuesta.CDE1012)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1012)
	private BigDecimal montoMensual;
	
	@JsonProperty("montoDiario")
	@NotNull(message = CodRespuesta.CDE1013)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1013)
	private BigDecimal montoDiario;
	
	@JsonProperty("montoBanco")
	@NotNull(message = CodRespuesta.CDE1014)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1014)
	private BigDecimal montoBanco;
	
	@JsonProperty("flagActivo")
	@NotNull(message=CodRespuesta.CDE1007)
	private Boolean flagActivo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
