package com.tecnica.mercadolibre.xmen.servicio;

import com.tecnica.mercadolibre.xmen.interfaces.ADNHistoricoInterface;
import com.tecnica.mercadolibre.xmen.modelo.ADNHistorio;
import com.tecnica.mercadolibre.xmen.respositorio.ADNHistorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ADNHistoricoServicio implements ADNHistoricoInterface {
    @Autowired
    private ADNHistorioRepository adnHistorioRepository;
    @Override
    public long countByResultado(String name) {
        return adnHistorioRepository.countByResultado(name);
    }
    @Override
    public void save(ADNHistorio obj){
        if(obj.getDna() != null){
            adnHistorioRepository.save(obj);
        }
    }
}
