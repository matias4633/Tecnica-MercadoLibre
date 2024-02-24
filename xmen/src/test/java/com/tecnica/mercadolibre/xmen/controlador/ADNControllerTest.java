package com.tecnica.mercadolibre.xmen.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnica.mercadolibre.xmen.DTO.ADNRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ADNControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void deberiaResponderADN_Vacio() throws Exception {
        ADNRequest request = new ADNRequest(new String[]{});

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.mensaje").value("No puede enviar el ADN vacio."))
                .andExpect(jsonPath("$.resultado").value("ADN_INVALIDO"));
    }


    @Test
    public void deberiaResponderADN_NoValidoPorMatrizNoCuadrada() throws Exception {
        ADNRequest request = new ADNRequest(new String[]{
                "ATGCATGC",
                "TGCATGCA",
                "GCATGCAT",
                "CATGCATG",
                "ATGCATGC",
                "TGCATGCA",
                "GCATGCAT"}
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.mensaje").value("El ADN recibido es invalido."))
                .andExpect(jsonPath("$.resultado").value("ADN_INVALIDO"));
    }

    @Test
    public void deberiaResponderADN_NoValidoPorCaracterNoValido() throws Exception {
        ADNRequest request = new ADNRequest(new String[]{
                "ATGCATGC",
                "TGCATGCA",
                "GCATGCAT",
                "CATGCATG",
                "ATGCATGC",
                "TGCATGCA",
                "GCATGCAT",
                "CATGCATX"}
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.mensaje").value("El ADN recibido es invalido."))
                .andExpect(jsonPath("$.resultado").value("ADN_INVALIDO"));
    }

    @Test
    public void deberiaResponderADN_NoValidoPorMatrizCuadara_N_MenorALoBuscado() throws Exception {
        ADNRequest request = new ADNRequest(new String[]{
                "ATG",
                "TGC",
                "GCA",}
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.mensaje").value("El ADN recibido es invalido."))
                .andExpect(jsonPath("$.resultado").value("ADN_INVALIDO"));
    }

    @Test
    public void deberiaResponderMUTANTE() throws Exception {
        ADNRequest request = new ADNRequest(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"});

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Es un mutante."))
                .andExpect(jsonPath("$.resultado").value("MUTANTE"));
    }

}