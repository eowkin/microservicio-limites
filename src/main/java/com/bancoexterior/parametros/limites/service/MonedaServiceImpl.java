package com.bancoexterior.parametros.limites.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancoexterior.parametros.limites.repository.IMonedaRepository;

@Service
public class MonedaServiceImpl implements IMonedaService{

	@Autowired
	private IMonedaRepository repo;
	
	@Override
	public boolean existsById(String codMoneda) {
		return repo.existsById(codMoneda);
	}

}
