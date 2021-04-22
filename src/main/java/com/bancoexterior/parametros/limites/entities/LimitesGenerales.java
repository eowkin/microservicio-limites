package com.bancoexterior.parametros.limites.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bancoexterior.parametros.limites.config.Codigos.CodRespuesta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "\"Limites_generales\"", schema = "\"Convenio1\"")
public class LimitesGenerales {
	@EmbeddedId
	private LimitesGeneralesPk id;
	
	@NotNull(message = CodRespuesta.CDE1009)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1009)
	@Column(name="monto_min", nullable = false)
	private BigDecimal montoMin;
	
	@NotNull(message = CodRespuesta.CDE1010)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1010)
	@Column(name="monto_max", nullable = false)
	private BigDecimal montoMax;
	
	@NotNull(message = CodRespuesta.CDE1011)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1011)
	@Column(name="monto_tope", nullable = false)
	private BigDecimal montoTope;
	
	@NotNull(message = CodRespuesta.CDE1012)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1012)
	@Column(name="monto_mensual", nullable = false)
	private BigDecimal montoMensual;
	
	@NotNull(message = CodRespuesta.CDE1013)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1013)
	@Column(name="monto_diario", nullable = false)
	private BigDecimal montoDiario;
	
	@NotNull(message = CodRespuesta.CDE1014)
	@Digits(integer=13, fraction=2, message = CodRespuesta.CDE1014)
	@Column(name="monto_banco", nullable = false)
	private BigDecimal montoBanco;
	
	@NotEmpty(message = "no puede ser vacio")
	@Column(name = "cod_usuario", nullable = false)
	@Size(max = 10)
	private String codUsuario;
	
	//@NotEmpty(message = "no puede ser vacio")
	@Column(name = "flag_activo", nullable = false)
	private Boolean flagActivo;
	
	@Column(name = "fecha_modificacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	
	
	
	@PrePersist
	public void prePersist() {
		setFechaModificacion(new Date());
	}
	
	@PreUpdate
	public void preUpdate() {
		setFechaModificacion(new Date());
	}

}
