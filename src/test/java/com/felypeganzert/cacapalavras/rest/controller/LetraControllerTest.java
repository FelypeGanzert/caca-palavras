package com.felypeganzert.cacapalavras.rest.controller;

import static com.felypeganzert.cacapalavras.util.ResponseBodyMatchers.responseBody;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.LETRA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;
import com.felypeganzert.cacapalavras.services.LetraService;

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
@WebMvcTest(controllers = LetraController.class)
public class LetraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LetraService service;

    @MockBean
    private CacaPalavrasPayloadMapper payloadMapper;

    private static final int ID_LETRA = 1;
    private static final int ID_TABULEIRO = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    private static final String BASE_PATH = "/api/caca-palavras/" + ID_CACA_PALAVRAS + "/tabuleiro/" + ID_TABULEIRO + "/letras";
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.adicionarLetra(
                    ArgumentMatchers.any(LetraDTO.class),
                    ArgumentMatchers.any(Integer.class),
                    ArgumentMatchers.any(Integer.class))
                ).thenReturn(criarLetraDTO());
    }

    @Test
    void deveRetornarStatus201AoAdicionarComSucessoQuandoValido() throws JsonProcessingException, Exception{
        LetraDTO letraDTO = criarLetraDTO();
        LetraPostDTO dto = criarLetraPostDTO();

        BDDMockito.when(payloadMapper.toLetraDTO(ArgumentMatchers.any(LetraPostDTO.class))).thenReturn(letraDTO);

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(responseBody().contemObjetoComoJson(letraDTO, LetraDTO.class));

        ArgumentCaptor<LetraDTO> letraCaptor = ArgumentCaptor.forClass(LetraDTO.class);
        verify(service).adicionarLetra(letraCaptor.capture(), anyInt(), anyInt());
        
        assertThat(letraCaptor.getValue().getLetra()).isEqualTo(dto.getLetra());
        assertThat(letraCaptor.getValue().getPosicaoX()).isEqualTo(dto.getPosicaoX());
        assertThat(letraCaptor.getValue().getPosicaoY()).isEqualTo(dto.getPosicaoY());

        verify(service, times(1)).adicionarLetra(letraDTO, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus400EErroAoInformarPosicaoXMenorQueUm() throws JsonProcessingException, Exception{
        LetraPostDTO dto = LetraPostDTO.builder().letra('s').posicaoX(0).posicaoY(1).build();

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(responseBody().contemErro("Posição X não pode ser igual ou menor a 0"));

        verify(service, never()).adicionarLetra(any(LetraDTO.class), anyInt(), anyInt());
    }

    @Test
    void deveRetornarStatus400EErroAoInformarPosicaoYMenorQueUm() throws JsonProcessingException, Exception{
        LetraPostDTO dto = LetraPostDTO.builder().letra('s').posicaoX(1).posicaoY(0).build();

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(responseBody().contemErro("Posição Y não pode ser igual ou menor a 0"));

        verify(service, never()).adicionarLetra(any(LetraDTO.class), anyInt(), anyInt());
    }

    @Test
    void deveRetornarStatus201AoAdicionarEmLoteComSucessoQuandoValido() throws JsonProcessingException, Exception{
        List<LetraPostDTO> letrasParaAdicionar = new ArrayList<>();
        LetraPostDTO dto1 = LetraPostDTO.builder().letra('c').posicaoX(1).posicaoY(1).build();
        LetraPostDTO dto2 = LetraPostDTO.builder().letra('s').posicaoX(1).posicaoY(2).build();
        letrasParaAdicionar.addAll(java.util.Arrays.asList(dto1, dto2));

        List<LetraDTO> letraMapeadas = letrasParaAdicionar.stream()
                                    .map(dto -> LetraDTO.builder()
                                                    .letra(dto.getLetra())
                                                    .posicaoX(dto.getPosicaoX())
                                                    .posicaoY(dto.getPosicaoY())
                                                    .build()
                                    ).collect(Collectors.toList());
        BDDMockito.when(payloadMapper.toLetrasDTO(letrasParaAdicionar)).thenReturn(letraMapeadas);
        BDDMockito.when(service.adicionarLetras(letraMapeadas, ID_TABULEIRO, ID_CACA_PALAVRAS)).thenReturn(letraMapeadas);

        mockMvc.perform(post(BASE_PATH + "/adicionar-em-lote")
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(letrasParaAdicionar)))
            .andExpect(status().isCreated());

        verify(service, times(1)).adicionarLetras(letraMapeadas, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus400EErroAoInformarPeloMenosUmComPosicaoXMenorQueUm() throws JsonProcessingException, Exception{
        List<LetraPostDTO> letrasParaAdicionar = new ArrayList<>();
        LetraPostDTO dto1 = LetraPostDTO.builder().letra('c').posicaoX(0).posicaoY(1).build();
        LetraPostDTO dto2 = LetraPostDTO.builder().letra('s').posicaoX(1).posicaoY(2).build();
        letrasParaAdicionar.addAll(java.util.Arrays.asList(dto1, dto2));

        mockMvc.perform(post(BASE_PATH + "/adicionar-em-lote")
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(letrasParaAdicionar)))
            .andExpect(status().isBadRequest())
            .andExpect(responseBody().contemErro("Posição X não pode ser igual ou menor a 0"));

        verify(service, never()).adicionarLetras(any(), anyInt(), anyInt());
    }

    @Test
    void deveRetornarStatus400EErroAoInformarPeloMenosUmComPosicaoYMenorQueUm() throws JsonProcessingException, Exception{
        List<LetraPostDTO> letrasParaAdicionar = new ArrayList<>();
        LetraPostDTO dto1 = LetraPostDTO.builder().letra('c').posicaoX(1).posicaoY(0).build();
        LetraPostDTO dto2 = LetraPostDTO.builder().letra('s').posicaoX(1).posicaoY(0).build();
        letrasParaAdicionar.addAll(java.util.Arrays.asList(dto1, dto2));

        mockMvc.perform(post(BASE_PATH + "/adicionar-em-lote")
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(letrasParaAdicionar)))
            .andExpect(status().isBadRequest())
            .andExpect(responseBody().contemErro("Posição Y não pode ser igual ou menor a 0"));

        verify(service, never()).adicionarLetras(any(), anyInt(), anyInt());
    }

    @Test
    void deveRetornarStatus200EChamarFindAllDoServiceComSucesso() throws Exception{
        mockMvc.perform(get(BASE_PATH)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(service).findAll(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus200AoBuscarPorIdExistente() throws Exception {
        LetraDTO letraDTOEsperado = criarLetraDTO();

        BDDMockito.when(service.findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS)).thenReturn(letraDTOEsperado);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_LETRA )
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().contemObjetoComoJson(letraDTOEsperado, LetraDTO.class));

        verify(service).findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus404AoBuscarPorIdNaoExistente() throws Exception {
        RecursoNaoEncontradoException excEsperada = new RecursoNaoEncontradoException(LETRA, ID, ID_LETRA);

        BDDMockito.when(service.findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS)).thenThrow(excEsperada);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_LETRA)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().contemErro(excEsperada.getMessage()));

        verify(service).findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus200AoAtualizarUmaPalavraExitente() throws Exception{
        LetraDTO letraDTOEsperado = criarLetraDTO();
        
        LetraPutDTO dto = LetraPutDTO.builder().letra('s').build();

        BDDMockito.when(service.atualizar(dto.getLetra(), ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS)).thenReturn(letraDTOEsperado);
        mockMvc.perform(put(BASE_PATH +"/{id}", ID_LETRA )
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(responseBody().contemObjetoComoJson(letraDTOEsperado, LetraDTO.class));
    
      
        verify(service).atualizar(dto.getLetra(), ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus204AoDeletar() throws Exception {
        mockMvc.perform(delete(BASE_PATH +"/{id}", ID_LETRA)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).delete(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus204AoDeletarTodasAsPalavrasDeUmCacaPalavras() throws Exception {
        mockMvc.perform(delete(BASE_PATH)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).deleteAll(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    private LetraDTO criarLetraDTO(){
        return LetraDTO.builder().letra('s').posicaoX(1).posicaoY(2).build();
    }

    private LetraPostDTO criarLetraPostDTO(){
        return LetraPostDTO.builder().letra('s').posicaoX(1).posicaoY(2).build();
    }

}
