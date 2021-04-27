package com.bancoexterior.parametros.limites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancoexterior.parametros.limites.entities.LimitesGenerales;
import com.bancoexterior.parametros.limites.entities.LimitesGeneralesPk;


@Repository
public interface ILimitesGeneralesRepository extends JpaRepository<LimitesGenerales, LimitesGeneralesPk>{
	String queryAll = "select new com.bancoexterior.parametros.limites.dto.LimitesGeneralesDto"
			+ "(t.id.codMoneda, t.id.tipoTransaccion, t.id.tipoCliente, t.montoMin, t.montoMax, t.montoTope, "
			+ "t.montoMensual, t.montoDiario, t.montoBanco, t.codUsuario, t.flagActivo, t.fechaModificacion) "
			+ " from LimitesGenerales t"
			+ " where 1=1";
	
	
	String queryNativo = "SELECT cod_moneda, tipo_transaccion, tipo_cliente, monto_min, monto_max, monto_tope, monto_mensual, monto_diario, monto_banco, cod_usuario, fecha_modificacion, flag_activo "
			+ "FROM \"Convenio1\".\"Limites_generales\" "
			+ "where cod_moneda= (case when ?1 = '' then cod_moneda else ?1 end) "
			+ "and tipo_transaccion= (case when ?2 = '' then tipo_transaccion else ?2 end) "
			+ "and tipo_cliente= (case when ?3 = '' then tipo_cliente else ?3 end) "
			+ "and "
			+ "case when  ?4 = 'si' then "
			+ "	flag_activo= ?5 "
			+ "else "
			+ "	flag_activo = flag_activo "
			+ "end";
	
	@Query(value = queryNativo, nativeQuery = true)
	public List<LimitesGenerales> getLimitesByNuevo(String codMoneda, String tipoTransaccion, String naturaleza, String flag, boolean flagActivo);
	
	
	
	
}
