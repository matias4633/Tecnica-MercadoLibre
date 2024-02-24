package com.tecnica.mercadolibre.xmen.servicio;

import com.tecnica.mercadolibre.xmen.DTO.ADNRequest;
import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import com.tecnica.mercadolibre.xmen.interfaces.ADNAnalizadorInterface;
import com.tecnica.mercadolibre.xmen.modelo.ADNHistorio;
import com.tecnica.mercadolibre.xmen.respositorio.ADNHistorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ADNAnalizadorServicio implements ADNAnalizadorInterface {

    private static final Set<Character> caracteresValidos = Set.of('A', 'T', 'C', 'G');
    private static final int MINIMO_SECUENCIA = 4;

    @Autowired
    private ADNHistorioRepository adnHistorioRepository;

    public boolean isMutant(String[] dna){
        return iniciarProceso(dna);
    }

    @Override
    public TipoResultado procesarADN(ADNRequest request){
        ADNHistorio historio = new ADNHistorio();
        TipoResultado resultado = iniciarProceso(request , historio);
        adnHistorioRepository.save(historio);
        return resultado;
    }

    public boolean iniciarProceso(String[] dna) {
        TipoResultado resultado = TipoResultado.ADN_INVALIDO;
        if(esADNValido(dna)){
            if(tieneSecuenciaHorizontal(dna)|| tieneSecuenciaVertical(dna) || tieneSecuenciaDiagonal(dna)){
                resultado = TipoResultado.MUTANTE;
            }else{
                resultado = TipoResultado.NO_MUTANTE;
            }
        }
        return resultado.equals(TipoResultado.MUTANTE);
    }


    public TipoResultado iniciarProceso(ADNRequest request, ADNHistorio historio) {
        TipoResultado resultado = TipoResultado.ADN_INVALIDO;
        if(esADNValido(request.getDna())){
            if(tieneSecuenciaHorizontal(request.getDna()) || tieneSecuenciaVertical(request.getDna()) || tieneSecuenciaDiagonal(request.getDna())){
                resultado = TipoResultado.MUTANTE;
            }else{
                resultado = TipoResultado.NO_MUTANTE;
            }
        }
        historio.setResultado(resultado);
        historio.setDna(request.getDna());
        return resultado;
    }

    private boolean tieneSecuenciaVertical(String[] dna) {
        int n = dna.length;
        for (int i = 0; i < n; i++) {
            StringBuilder columnBuilder = new StringBuilder();
            for (String row : dna) {
                columnBuilder.append(row.charAt(i));
            }
            String columna = columnBuilder.toString();
            if (tieneSecuenciaRepetida(columna)) return true;
        }
        return false;
    }

    /**
     * Varifica si tiene una secuncia diagonalmente tomando primero la diagonal principal y luego las diagonales superoires e inferiores.
     * @param dna
     * @return
     */
    private boolean tieneSecuenciaDiagonal(String[] dna) {
        int n = dna.length;
        for (int i = 0; i <= n - MINIMO_SECUENCIA; i++) {
            StringBuilder diagonalBuilder1 = new StringBuilder();
            StringBuilder diagonalBuilder2 = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                diagonalBuilder1.append(dna[j].charAt(j + i));
                diagonalBuilder2.append(dna[j + i].charAt(j));
            }
            if (tieneSecuenciaRepetida(diagonalBuilder1.toString())) return true;
            if (tieneSecuenciaRepetida(diagonalBuilder2.toString())) return true;
        }
        return false;
    }

    /**
     * Verificaca si existe una secuencia repetidoa horizontalmente
     * @param dna
     * @return
     */
    private boolean tieneSecuenciaHorizontal(String[] dna) {
        for (String row : dna) {
            if (tieneSecuenciaRepetida(row)) return true;
        }
        return false;
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
