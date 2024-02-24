package com.tecnica.mercadolibre.xmen.controlador;

import com.tecnica.mercadolibre.xmen.DTO.EstadisticaDTO;
import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import com.tecnica.mercadolibre.xmen.servicio.ADNHistoricoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class EstadisticaController {
    @Autowired
    private ADNHistoricoServicio adnHistoricoServicio;

    @GetMapping
    public ResponseEntity<EstadisticaDTO> obtenerEstadisticas() {
        long countMutantes = adnHistoricoServicio.countByResultado(TipoResultado.MUTANTE.name());
        long countHumanos = adnHistoricoServicio.countByResultado(TipoResultado.NO_MUTANTE.name());

        double ratio = countMutantes / (double)(countMutantes + countHumanos);

        EstadisticaDTO estadistica = new EstadisticaDTO();
        estadistica.setCount_mutant_dna(countMutantes);
        estadistica.setCount_human_dna(countHumanos);
        estadistica.setRatio(ratio);

        return ResponseEntity.ok().body(estadistica);
    }
}
