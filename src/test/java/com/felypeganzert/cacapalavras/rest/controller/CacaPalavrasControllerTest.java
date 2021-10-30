package com.felypeganzert.cacapalavras.rest.controller;

import static com.felypeganzert.cacapalavras.util.ResponseBodyMatchers.responseBody;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CacaPalavrasController.class)
public class CacaPalavrasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CacaPalavrasService service;

    @MockBean
    private CacaPalavrasRepository repository;

    @MockBean
    private CacaPalavrasMapper cacaPalavrasMapper;

    @MockBean
    private CacaPalavrasPayloadMapper payloadMapper;

    private static final Integer ID_CACA_PALAVRAS = 1;

    private static final String BASE_PATH = "/api/caca-palavras";
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.criarComBasico(any(CacaPalavrasDTO.class))).thenReturn(criarCacaPalavrasValido());

        BDDMockito.when(payloadMapper.toCacaPalavrasDTO(any(CacaPalavrasPostDTO.class)))
            .thenReturn(criarCacaPalavrasDTOValido());
    }

    @Test
    void deveRetornarStatus201ComSucessoQuandoValido() throws JsonProcessingException, Exception {
        CacaPalavrasPostDTO dto = criarCacaPalavrasPostDTOValido();
        Integer idCriadoEsperado = ID_CACA_PALAVRAS;

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(responseBody().contemObjetoComoJson(idCriadoEsperado, Integer.class));;

        ArgumentCaptor<CacaPalavrasDTO> cacaPalavrasCaptor = ArgumentCaptor.forClass(CacaPalavrasDTO.class);
        verify(service).criarComBasico(cacaPalavrasCaptor.capture());
        
        assertThat(cacaPalavrasCaptor.getValue().getCriador()).isEqualTo(dto.getCriador());
        assertThat(cacaPalavrasCaptor.getValue().getTitulo()).isEqualTo(dto.getTitulo());

        verify(service).criarComBasico(any(CacaPalavrasDTO.class));
    }

    @Test
    void deveRetornarStatus400EErroAoNaoInformarOCriador() throws Exception {
        CacaPalavrasPostDTO dto = CacaPalavrasPostDTO.builder().titulo("Título").build();
        mockMvc.perform(post(BASE_PATH)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().contemErro("Criador não pode ser vazio"));
    }

    @Test
    void deveRetornarStatus400EErroAoNaoInformarOTitulo() throws Exception {
        CacaPalavrasPostDTO dto = CacaPalavrasPostDTO.builder().criador("Criador").build();
        mockMvc.perform(post(BASE_PATH)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().contemErro("Título não pode ser vazio"));
    }

    @Test
    void deveRetornarStatus200EChamarBuscarInformacoesBasicasDoServiceComSucesso() throws Exception {
        mockMvc.perform(get(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAllComInformacoesBasicas();
    }

    @Test
    void deveRetornarStatus200AoBuscarPorIdExistente() throws Exception {
        CacaPalavras cacaPalavrasEsperado = criarCacaPalavrasValido();
        CacaPalavrasDTO cacaPalavrasDTOEsperado = criarCacaPalavrasDTOValido();
        

        BDDMockito.when(service.findById(ID_CACA_PALAVRAS)).thenReturn(cacaPalavrasEsperado);
        BDDMockito.when(cacaPalavrasMapper.toCacaPalavrasDTO(cacaPalavrasEsperado)).thenReturn(cacaPalavrasDTOEsperado);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_CACA_PALAVRAS )
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().contemObjetoComoJson(cacaPalavrasDTOEsperado, CacaPalavrasDTO.class));

        verify(service).findById(ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus404AoBuscarPorIdNaoExistente() throws Exception {
        RecursoNaoEncontradoException excEsperada = new RecursoNaoEncontradoException(CACA_PALAVRAS, ID, ID_CACA_PALAVRAS);

        BDDMockito.when(service.findById(ID_CACA_PALAVRAS)).thenThrow(excEsperada);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_CACA_PALAVRAS)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().contemErro(excEsperada.getMessage()));

        verify(service).findById(ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus204AoDeletar() throws Exception {
        mockMvc.perform(delete(BASE_PATH +"/{id}", ID_CACA_PALAVRAS)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).delete(ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus200AoSolucionar() throws Exception {
        mockMvc.perform(patch(BASE_PATH +"/{id}/solucionar", ID_CACA_PALAVRAS)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).resolverCacaPalavras(ID_CACA_PALAVRAS);
    }

    private CacaPalavrasPostDTO criarCacaPalavrasPostDTOValido() {
        return CacaPalavrasPostDTO.builder().criador("Teste Criador").titulo("Teste Título").build();
    }

    private CacaPalavrasDTO criarCacaPalavrasDTOValido() {
        return CacaPalavrasDTO.builder().criador("Teste Criador").titulo("Teste Título").build();
    }

    private CacaPalavras criarCacaPalavrasValido(){
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setId(ID_CACA_PALAVRAS);
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador("Teste Criador");
        cacaPalavras.setTitulo("Teste Título");
        return cacaPalavras;
    }

}
