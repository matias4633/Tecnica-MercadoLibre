package com.tecnica.mercadolibre.xmen.controlador;

import com.tecnica.mercadolibre.xmen.enumable.TipoResultado;
import com.tecnica.mercadolibre.xmen.servicio.ADNHistoricoServicio;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class EstadisticaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ADNHistoricoServicio adnHistoricoServicioMock;
    @Test
    public void deberiaResponderLasEstadisticasCorrectas() throws Exception {
        when(adnHistoricoServicioMock.countByResultado(TipoResultado.MUTANTE.name())).thenReturn(40L);
        when(adnHistoricoServicioMock.countByResultado(TipoResultado.NO_MUTANTE.name())).thenReturn(100L);
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }
}