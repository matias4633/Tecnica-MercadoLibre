package com.tecnica.mercadolibre.xmen.enumable;

import lombok.Getter;

@Getter
public enum TipoResultado {
    MUTANTE("Mutante", "Es un mutante."),
    NO_MUTANTE("No Mutante" , "No es un mutante."),

    ADN_INVALIDO("ADN INVALIDO", "El ADN enviado es invalido.");

    private String resultado;
    private String mensaje;

    TipoResultado ( String resultado , String mensaje){
        this.resultado = resultado;
        this.mensaje = mensaje;
    }
}
