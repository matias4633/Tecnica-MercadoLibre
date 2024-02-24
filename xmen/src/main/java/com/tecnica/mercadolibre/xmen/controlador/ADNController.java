package com.tecnica.mercadolibre.xmen.controlador;

import com.tecnica.mercadolibre.xmen.DTO.ADNRequest;
import com.tecnica.mercadolibre.xmen.DTO.ApiResponse;
import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import com.tecnica.mercadolibre.xmen.excepcion.ADNNoValidoException;
import com.tecnica.mercadolibre.xmen.interfaces.ADNAnalizadorInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class ADNController {

    @Autowired
    private ADNAnalizadorInterface ADNServicio;

    @PostMapping()
    public ResponseEntity<ApiResponse> procesarADN(@RequestBody @Valid ADNRequest request){
        TipoResultado tipoResultado = ADNServicio.procesarADN(request.getDna());
        if(tipoResultado.equals(TipoResultado.ADN_INVALIDO)){
            throw new ADNNoValidoException();
        }
        return ResponseEntity.ok().body(new ApiResponse(tipoResultado.getMensaje() , tipoResultado.name()));
    }
}
