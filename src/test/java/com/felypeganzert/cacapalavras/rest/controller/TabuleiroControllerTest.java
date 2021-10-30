package com.felypeganzert.cacapalavras.rest.controller;

import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.ALTURA_MINIMA;
import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.LARGURA_MINIMA;
import static com.felypeganzert.cacapalavras.util.ResponseBodyMatchers.responseBody;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TabuleiroController.class)
public class TabuleiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TabuleiroService service;

    @MockBean
    private CacaPalavrasMapper cacaPalavrasMapper;

    @MockBean
    private CacaPalavrasPayloadMapper payloadMapper;

    private static final int ID_TABULEIRO = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    private static final String BASE_PATH = "/api/caca-palavras/" + ID_CACA_PALAVRAS + "/tabuleiro";
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.criarComBasico(ArgumentMatchers.any(TabuleiroDTO.class),
                ArgumentMatchers.any(Integer.class))).thenReturn(criarTabuleiroValido());
                
        BDDMockito.when(payloadMapper.toTabuleiroDTO(ArgumentMatchers.any(TabuleiroPostDTO.class)))
                .thenReturn(criarTabuleiroDTOValido());
    }

    @Test
    void deveRetornarStatus201AoCriarComBasicoQuandoValido() throws JsonProcessingException, Exception{
        TabuleiroPostDTO dto = TabuleiroPostDTO.builder().altura(ALTURA_MINIMA).largura(LARGURA_MINIMA).build();
        Integer idCriadoEsperado = ID_TABULEIRO;

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(responseBody().contemObjetoComoJson(idCriadoEsperado, Integer.class));

        ArgumentCaptor<TabuleiroDTO> tabuleiroCaptor = ArgumentCaptor.forClass(TabuleiroDTO.class);
        verify(service).criarComBasico(tabuleiroCaptor.capture(), any());
        
        assertThat(tabuleiroCaptor.getValue().getLargura()).isEqualTo(dto.getLargura());
        assertThat(tabuleiroCaptor.getValue().getAltura()).isEqualTo(dto.getAltura());

        verify(service).criarComBasico(any(TabuleiroDTO.class), any(Integer.class));
    }

    @Test
    void deveRetornarStatus400EErroAonInformarLarguraMenorQueAMinima() throws JsonProcessingException, Exception{
        TabuleiroPostDTO dto = TabuleiroPostDTO.builder().altura(ALTURA_MINIMA).largura(LARGURA_MINIMA - 1).build();

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(responseBody().contemErro("Largura precisa ser no mínimo " + LARGURA_MINIMA));

        verify(service, never()).criarComBasico(any(TabuleiroDTO.class), any(Integer.class));
    }

    @Test
    void deveRetornarStatus400EErroAonInformarAlturaMenorQueAMinima() throws JsonProcessingException, Exception{
        TabuleiroPostDTO dto = TabuleiroPostDTO.builder().altura(ALTURA_MINIMA - 1).largura(LARGURA_MINIMA).build();

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(responseBody().contemErro("Altura precisa ser no mínimo " + ALTURA_MINIMA));

        verify(service, never()).criarComBasico(any(TabuleiroDTO.class), any(Integer.class));
    }

    @Test
    void deveRetornarStatus200AoBuscarPorIdExistente() throws Exception {
        Tabuleiro tabuleiroEsperado = criarTabuleiroValido();
        TabuleiroDTO tabuleiroDTOEsperado = criarTabuleiroDTOValido();

        BDDMockito.when(service.findById(ID_TABULEIRO, ID_CACA_PALAVRAS)).thenReturn(tabuleiroEsperado);
        BDDMockito.when(cacaPalavrasMapper.toTabuleiroDTO(tabuleiroEsperado)).thenReturn(tabuleiroDTOEsperado);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_TABULEIRO )
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().contemObjetoComoJson(tabuleiroDTOEsperado, TabuleiroDTO.class));

        verify(service).findById(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus404AoBuscarPorIdNaoExistente() throws Exception {
        RecursoNaoEncontradoException excEsperada = new RecursoNaoEncontradoException(TABULEIRO, ID, ID_TABULEIRO);

        BDDMockito.when(service.findById(ID_TABULEIRO, ID_CACA_PALAVRAS)).thenThrow(excEsperada);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_TABULEIRO)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().contemErro(excEsperada.getMessage()));

        verify(service).findById(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus204AoDeletar() throws Exception {
        mockMvc.perform(delete(BASE_PATH +"/{id}", ID_TABULEIRO)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).delete(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    private TabuleiroDTO criarTabuleiroDTOValido(){
        return TabuleiroDTO.builder().largura(LARGURA_MINIMA).altura(ALTURA_MINIMA).build();
    }

    private Tabuleiro criarTabuleiroValido() {
        int largura = LARGURA_MINIMA;
        int altura = ALTURA_MINIMA;
        Tabuleiro tabuleiro = new Tabuleiro(ID_TABULEIRO, largura, altura);
        return tabuleiro;
    }

}
