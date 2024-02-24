package com.tecnica.mercadolibre.xmen.interfaces;

import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public interface ADNAnalizadorInterface {

    TipoResultado procesarADN(@NotNull @NotEmpty String[] request);
}
