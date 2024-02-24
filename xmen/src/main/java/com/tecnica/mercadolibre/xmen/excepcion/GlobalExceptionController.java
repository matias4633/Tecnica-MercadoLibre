package com.tecnica.mercadolibre.xmen.excepcion;

import com.tecnica.mercadolibre.xmen.DTO.ApiResponse;
import com.tecnica.mercadolibre.xmen.diccionario.Texto_;
import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ADNNoValidoException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse> handleADNNoValido(ADNNoValidoException e){
        return ResponseEntity.status(403).body(new ApiResponse(e.getMensaje() , TipoResultado.ADN_INVALIDO.name()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse> handleADNNoValido(MethodArgumentNotValidException e){
        return ResponseEntity.status(403).body(new ApiResponse(Texto_.ADN_VACIO, TipoResultado.ADN_INVALIDO.name()));
    }

}
