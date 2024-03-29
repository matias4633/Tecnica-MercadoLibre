package com.tecnica.mercadolibre.xmen.servicio;

import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import com.tecnica.mercadolibre.xmen.interfaces.ADNAnalizadorInterface;
import com.tecnica.mercadolibre.xmen.modelo.ADNHistorio;
import com.tecnica.mercadolibre.xmen.utils.Secuenciero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ADNAnalizadorServicio implements ADNAnalizadorInterface {

    private static final Set<Character> CARACTERES_VALIDOS = Set.of('A', 'T', 'C', 'G');
    private static final int MINIMO_SECUENCIA = 4;

    private static final int SECUENCIAS_MINIMAS_REQUERIDAS = 2;

    @Autowired
    private ADNHistoricoServicio adnHistoricoServicio;

    /**
     * Retorna true si el ADN es MUTANTE.
     * @param dna
     * @return
     */
    public boolean isMutant(String[] dna){
        TipoResultado resultado = iniciarProceso(dna , null);
        return resultado.equals(TipoResultado.MUTANTE);
    }

    /**
     * Inicia el proceso del ADN y guarda el historico consultado.
     * @param dna
     * @return
     */
    @Override
    public TipoResultado procesarADN(@NotNull @NotEmpty String[] dna){
        ADNHistorio historio = new ADNHistorio();
        TipoResultado resultado = iniciarProceso(dna , historio);
        adnHistoricoServicio.save(historio);
        return resultado;
    }

    /**
     * El método de procesamiento para buscar secuencias mutantes se enfoca en evitar el procesamiento innecesario de datos.
     * Tan pronto como se dispone de la información suficiente para tomar una decisión, el proceso termina.
     * Este enfoque optimiza el rendimiento al evitar operaciones redundantes.
     * Este método está diseñado para no procesar secuencias de ADN cuya longitud no alcance el umbral mínimo necesario para el éxito de la búsqueda.
     * Del mismo modo, los ciclos de procesamiento solo se activan cuando existe la posibilidad real de encontrar secuencias mutantes.
     * Esto asegura una ejecución eficiente del algoritmo al evitar la manipulación de datos que no contribuirán al resultado final.
     * @param dna
     * @param historio
     * @return
     */
    public TipoResultado iniciarProceso(String[] dna, ADNHistorio historio) {
        TipoResultado resultado = TipoResultado.ADN_INVALIDO;
        if(esADNValido(dna)){
            Secuenciero secuenciero = new Secuenciero(SECUENCIAS_MINIMAS_REQUERIDAS);
            tieneSecuenciaHorizontal(dna, secuenciero);
            if(!secuenciero.superaCantidadRequerida()) {
               tieneSecuenciaVertical(dna, secuenciero);
            }
            if(!secuenciero.superaCantidadRequerida()) {
               tieneSecuenciaDiagonal(dna, secuenciero);
            }
            if(!secuenciero.superaCantidadRequerida()) {
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

    /**
     * Buscar las secuencias verticales.
     * @param dna
     * @param secuenciero
     * @return
     */

    private void tieneSecuenciaVertical(String[] dna , Secuenciero secuenciero) {
        int n = dna.length;
        for (int i = 0; i < n; i++) {
            StringBuilder columnBuilder = new StringBuilder();
            for (String row : dna) {
                columnBuilder.append(row.charAt(i));
            }
            String columna = columnBuilder.toString();
            tieneSecuenciaRepetida(columna , secuenciero);
            if(secuenciero.superaCantidadRequerida()) break;
        }
    }

    /**
     * Varifica si tiene secuncias diagonalmente tomando primero la diagonal principal y luego las diagonales superoires e inferiores.
     * @param dna
     * @return
     */
    private void tieneSecuenciaDiagonal(String[] dna , Secuenciero secuenciero) {
        int n = dna.length;
        for (int i = 0; i <= n - MINIMO_SECUENCIA; i++) {
            StringBuilder diagonalBuilder1 = new StringBuilder();
            StringBuilder diagonalBuilder2 = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                diagonalBuilder1.append(dna[j].charAt(j + i));
                diagonalBuilder2.append(dna[j + i].charAt(j));
            }
            tieneSecuenciaRepetida(diagonalBuilder1.toString(), secuenciero);
            tieneSecuenciaRepetida(diagonalBuilder2.toString(), secuenciero);
            if (secuenciero.superaCantidadRequerida()) break;
        }
    }

    /**
     * Verificaca si existen secuencias repetidoa horizontalmente
     * @param dna
     * @return
     */
    private void tieneSecuenciaHorizontal(String[] dna , Secuenciero secuenciero) {
        for (String row : dna) {
            tieneSecuenciaRepetida(row , secuenciero);
            if(secuenciero.superaCantidadRequerida()) break;
        }
    }

    /**
     * Verifica si la secuencia tiene repetidos tiene N caractener repetidos.
     * Retprma ña camtodad actual encontrada.
     * @param secuencia
     * @return
     */
    private void tieneSecuenciaRepetida(String secuencia , Secuenciero secuenciero) {
        for (int i = 0; i <= secuencia.length() - MINIMO_SECUENCIA; i++) {
            if (secuencia.charAt(i) == secuencia.charAt(i + 1) &&
                    secuencia.charAt(i) == secuencia.charAt(i + 2) &&
                    secuencia.charAt(i) == secuencia.charAt(i + 3)) {
                secuenciero.sumar();
            }
            if(secuenciero.superaCantidadRequerida()) break;
        }
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
            if (!CARACTERES_VALIDOS.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
