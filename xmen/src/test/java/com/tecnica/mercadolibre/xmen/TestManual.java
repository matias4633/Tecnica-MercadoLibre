package com.tecnica.mercadolibre.xmen;

import com.tecnica.mercadolibre.xmen.servicio.ADNAnalizadorServicio;

/**
 * Clase por si quieren testear el servicio manualmente.
 */
public class TestManual {
    public static void main(String[] args) {
       ADNAnalizadorServicio servicio = new ADNAnalizadorServicio();
       boolean resultado = servicio.isMutant(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"});
       System.out.println(resultado);
    }
}
