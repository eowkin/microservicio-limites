package com.bancoexterior.parametros.limites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancoexterior.parametros.limites.config.Codigos.SQLUtils;
import com.bancoexterior.parametros.limites.entities.LimitesGenerales;
import com.bancoexterior.parametros.limites.entities.LimitesGeneralesPk;


@Repository
public interface ILimitesGeneralesRepository extends JpaRepository<LimitesGenerales, LimitesGeneralesPk>{
		
	@Query(value = SQLUtils.SELECTLIMITES, nativeQuery = true)
	public List<LimitesGenerales> getLimitesByNuevo(String codMoneda, String tipoTransaccion, String naturaleza, String flag, boolean flagActivo);
	
	
	
	
}
