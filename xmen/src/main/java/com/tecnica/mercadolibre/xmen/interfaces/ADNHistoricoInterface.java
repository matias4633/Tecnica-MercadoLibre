package com.tecnica.mercadolibre.xmen.interfaces;

import com.tecnica.mercadolibre.xmen.modelo.ADNHistorio;

public interface ADNHistoricoInterface {
    long countByResultado(String name);

    void save(ADNHistorio historio);
}
