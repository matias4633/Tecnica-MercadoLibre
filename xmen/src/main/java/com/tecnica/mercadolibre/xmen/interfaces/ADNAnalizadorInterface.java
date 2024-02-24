package com.tecnica.mercadolibre.xmen.interfaces;

import com.tecnica.mercadolibre.xmen.DTO.ADNRequest;
import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;

public interface ADNAnalizadorInterface {

    TipoResultado procesarADN(ADNRequest request);
}
