package com.bancoexterior.parametros.limites.interfase;

import com.bancoexterior.parametros.limites.model.WSRequest;
import com.bancoexterior.parametros.limites.model.WSResponse;

public interface  IWSService {
	WSResponse post(WSRequest request) ;
}
