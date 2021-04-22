package com.bancoexterior.parametros.limites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancoexterior.parametros.limites.entities.Moneda;

@Repository
public interface IMonedaRepository extends JpaRepository<Moneda, String>{

}
