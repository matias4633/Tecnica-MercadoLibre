package com.tecnica.mercadolibre.xmen.utils;

public class Secuenciero {
    private int cantidad;
    private final int CANTIDAD_REQUERIDA;

    public Secuenciero(int cantidadRequerida){
        this.CANTIDAD_REQUERIDA = cantidadRequerida;
        this.cantidad = 0;
    }

    public void sumar(){
        this.cantidad++;
    }

    public boolean superaCantidadRequerida(){
        return this.cantidad >= this.CANTIDAD_REQUERIDA;
    }
}
