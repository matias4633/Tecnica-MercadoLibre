package com.tecnica.mercadolibre.xmen.respositorio;

import com.tecnica.mercadolibre.xmen.modelo.ADNHistorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ADNHistorioRepository extends MongoRepository<ADNHistorio,String> {
    long countByResultado(String mutante);
}
