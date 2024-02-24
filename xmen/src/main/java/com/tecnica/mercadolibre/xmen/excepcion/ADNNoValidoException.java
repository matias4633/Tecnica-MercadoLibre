package com.tecnica.mercadolibre.xmen.excepcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ADNNoValidoException extends  RuntimeException{
    private String mensaje;

    public ADNNoValidoException(){
        super();
        this.mensaje = "El ADN recibido es invalido.";
    }
}
