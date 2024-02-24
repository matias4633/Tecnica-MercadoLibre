package com.tecnica.mercadolibre.xmen.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "adn_historico")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ADNHistorio {
    @JsonIgnore
    private String id;
    private TipoResultado resultado;
    private String[] dna;
}
