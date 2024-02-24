package com.tecnica.mercadolibre.xmen.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ADNRequest {
    @NotNull
    @NotEmpty
    private String[] dna;
}
