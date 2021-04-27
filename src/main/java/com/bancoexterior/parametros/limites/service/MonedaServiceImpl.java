package com.bancoexterior.parametros.limites.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancoexterior.parametros.limites.repository.IMonedaRepository;

@Service
public class MonedaServiceImpl implements IMonedaService{

	@Autowired
	private IMonedaRepository repo;
	
	
	
	/**
	 * Nombre: existsById 
	 * Descripcion: Invocar metodo para buscar si existe o no 
	 * una moneda por id.
	 * @param codMoneda String   
	 * @return boolean
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	@Override
	public boolean existsById(String codMoneda) {
		return repo.existsById(codMoneda);
	}

}
