package com.tecnica.mercadolibre.xmen.servicio;

import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import com.tecnica.mercadolibre.xmen.interfaces.ADNAnalizadorInterface;
import com.tecnica.mercadolibre.xmen.modelo.ADNHistorio;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ADNAnalizadorServicio implements ADNAnalizadorInterface {

    private static final Set<Character> caracteresValidos = Set.of('A', 'T', 'C', 'G');
    private static final int MINIMO_SECUENCIA = 4;

    private static final int SECUENCIAS_MINIMAS_REQUERIDAS = 2;

    @Autowired
    private ADNHistoricoServicio adnHistoricoServicio;

    public boolean isMutant(String[] dna){
        TipoResultado resultado = iniciarProceso(dna , null);
        return resultado.equals(TipoResultado.MUTANTE);
    }

    @Override
    public TipoResultado procesarADN(@NotNull @NotEmpty String[] dna){
        ADNHistorio historio = new ADNHistorio();
        TipoResultado resultado = iniciarProceso(dna , historio);
        adnHistoricoServicio.save(historio);
        return resultado;
    }
    public TipoResultado iniciarProceso(String[] dna, ADNHistorio historio) {
        TipoResultado resultado = TipoResultado.ADN_INVALIDO;
        if(esADNValido(dna)){
            int cantidad = 0;
            cantidad += tieneSecuenciaHorizontal(dna, cantidad);
            if(cantidad < SECUENCIAS_MINIMAS_REQUERIDAS) {
                cantidad += tieneSecuenciaVertical(dna, cantidad);
            }
            if(cantidad < SECUENCIAS_MINIMAS_REQUERIDAS) {
                cantidad += tieneSecuenciaDiagonal(dna, cantidad);
            }
            if(cantidad < SECUENCIAS_MINIMAS_REQUERIDAS) {
                resultado = TipoResultado.NO_MUTANTE;
            }else{
                resultado = TipoResultado.MUTANTE;
            }
        }
        if(historio != null){
            historio.setResultado(resultado);
            historio.setDna(dna);
        }
        return resultado;
    }

    private int tieneSecuenciaVertical(String[] dna , int cantidad) {
        int n = dna.length;
        for (int i = 0; i < n; i++) {
            StringBuilder columnBuilder = new StringBuilder();
            for (String row : dna) {
                columnBuilder.append(row.charAt(i));
            }
            String columna = columnBuilder.toString();
            if (tieneSecuenciaRepetida(columna)) cantidad++;
            if(cantidad>1) break;
        }
        return cantidad;
    }

    /**
     * Varifica si tiene secuncias diagonalmente tomando primero la diagonal principal y luego las diagonales superoires e inferiores.
     * @param dna
     * @return
     */
    private int tieneSecuenciaDiagonal(String[] dna , int cantidad) {
        int n = dna.length;
        for (int i = 0; i <= n - MINIMO_SECUENCIA; i++) {
            StringBuilder diagonalBuilder1 = new StringBuilder();
            StringBuilder diagonalBuilder2 = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                diagonalBuilder1.append(dna[j].charAt(j + i));
                diagonalBuilder2.append(dna[j + i].charAt(j));
            }
            if (tieneSecuenciaRepetida(diagonalBuilder1.toString())) cantidad++;
            if (tieneSecuenciaRepetida(diagonalBuilder2.toString())) cantidad++;
            if(cantidad>1) break;
        }
        return  cantidad;
    }

    /**
     * Verificaca si existen secuencias repetidoa horizontalmente
     * @param dna
     * @return
     */
    private int tieneSecuenciaHorizontal(String[] dna , int cantidad) {
        for (String row : dna) {
            if (tieneSecuenciaRepetida(row)) cantidad++;
            if(cantidad>1) break;
        }
        return  cantidad;
    }

    /**
     * Verifica si la secuencia tiene repetidos tiene N caractener repetidos.
     * @param secuencia
     * @return
     */
    private boolean tieneSecuenciaRepetida(String secuencia) {
        for (int i = 0; i <= secuencia.length() - MINIMO_SECUENCIA; i++) {
            if (secuencia.charAt(i) == secuencia.charAt(i + 1) &&
                    secuencia.charAt(i) == secuencia.charAt(i + 2) &&
                    secuencia.charAt(i) == secuencia.charAt(i + 3)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el ADN recibido cumple con los caracteres validos y con la condicion de ser NxN (Matriz cuadrada)
     * @param dna
     * @return
     */
    private boolean esADNValido(String[] dna) {
        if (dna == null || dna.length < MINIMO_SECUENCIA) return false;
        int n = dna.length;
        for (String row : dna) {
            if (row.length() != n || !esFilaValida(row)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica si la fila es valida en cuanto a sus caracteres.
     * @param row
     * @return
     */
    private boolean esFilaValida(String row) {
        for (char c : row.toCharArray()) {
            if (!caracteresValidos.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
