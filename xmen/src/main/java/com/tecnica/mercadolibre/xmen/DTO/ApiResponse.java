package com.tecnica.mercadolibre.xmen.DTO;

import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String mensaje;
    private String resultado;
}
