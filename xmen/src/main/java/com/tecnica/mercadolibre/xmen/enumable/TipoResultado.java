package com.tecnica.mercadolibre.xmen.enumable;

import com.tecnica.mercadolibre.xmen.diccionario.Texto_;
import lombok.Getter;

@Getter
public enum TipoResultado {
    MUTANTE(Texto_.ES_MUTANTE),
    NO_MUTANTE(Texto_.NO_MUTANTE),
    ADN_INVALIDO(Texto_.ADN_INVALIDO);

    private String mensaje;

    TipoResultado ( String mensaje){
        this.mensaje = mensaje;
    }
}
