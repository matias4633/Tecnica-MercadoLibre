package com.tecnica.mercadolibre.xmen.controlador;

import com.tecnica.mercadolibre.xmen.DTO.EstadisticaDTO;
import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import com.tecnica.mercadolibre.xmen.respositorio.ADNHistorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class EstadisticaController {
    @Autowired
    private ADNHistorioRepository adnHistoricoRepository;

    @GetMapping
    public ResponseEntity<EstadisticaDTO> obtenerEstadisticas() {
        long countMutantes = adnHistoricoRepository.countByResultado(TipoResultado.MUTANTE.name());
        long countHumanos = adnHistoricoRepository.countByResultado(TipoResultado.NO_MUTANTE.name());

        double ratio = countMutantes / (double)(countMutantes + countHumanos);

        EstadisticaDTO estadistica = new EstadisticaDTO();
        estadistica.setCount_mutant_dna(countMutantes);
        estadistica.setCount_human_dna(countHumanos);
        estadistica.setRatio(ratio);

        return ResponseEntity.ok().body(estadistica);
    }
}
