package com.felypeganzert.cacapalavras.rest.controller;


import static com.felypeganzert.cacapalavras.util.ResponseBodyMatchers.responseBody;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.PALAVRA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.rest.payload.PalavraRequestDTO;
import com.felypeganzert.cacapalavras.services.PalavraService;

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
@WebMvcTest(controllers = PalavraController.class)
public class PalavraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PalavraService service;

    @MockBean
    private CacaPalavrasMapper cacaPalavrasMapper;

    private static final int ID_PALAVRA = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    private static final String BASE_PATH = "/api/caca-palavras/" + ID_CACA_PALAVRAS + "/palavras";
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.adicionarPalavra(ArgumentMatchers.any(String.class),
                ArgumentMatchers.any(Integer.class))).thenReturn(criarPalavraValida());
    }

    @Test
    void deveRetornarStatus201AoAdicionarComSucessoQuandoValido() throws JsonProcessingException, Exception{
        PalavraRequestDTO dto = PalavraRequestDTO.builder().palavra("Sol").build();

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated());

        ArgumentCaptor<String> palavraCaptor = ArgumentCaptor.forClass(String.class);
        verify(service).adicionarPalavra(palavraCaptor.capture(), any());
        
        assertThat(palavraCaptor.getValue()).isEqualTo(dto.getPalavra());

        verify(service).adicionarPalavra(dto.getPalavra(), ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus400EErroAoNaoInformarPalavra() throws JsonProcessingException, Exception{
        PalavraRequestDTO dto = new PalavraRequestDTO();

        mockMvc.perform(post(BASE_PATH)
            .contentType(CONTENT_TYPE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(responseBody().contemErro("Palavra não pode ser vazia"));

        
        verify(service, never()).adicionarPalavra(dto.getPalavra(), ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus200EChamarFindAllDoServiceComSucesso() throws Exception{
        mockMvc.perform(get(BASE_PATH)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(service).findAll(ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus200AoBuscarPorIdExistente() throws Exception {
        Palavra palavraEsperada = criarPalavraValida();
        PalavraDTO palavraDTOEsperado = criarPalavraDTOValido();

        BDDMockito.when(service.findById(ID_PALAVRA, ID_CACA_PALAVRAS)).thenReturn(palavraEsperada);
        BDDMockito.when(cacaPalavrasMapper.toPalavraDTO(palavraEsperada)).thenReturn(palavraDTOEsperado);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_PALAVRA )
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().contemObjetoComoJson(palavraDTOEsperado, PalavraDTO.class));

        verify(service).findById(ID_PALAVRA, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus404AoBuscarPorIdNaoExistente() throws Exception {
        RecursoNaoEncontradoException excEsperada = new RecursoNaoEncontradoException(PALAVRA, ID, ID_PALAVRA);

        BDDMockito.when(service.findById(ID_PALAVRA, ID_CACA_PALAVRAS)).thenThrow(excEsperada);

        mockMvc.perform(get(BASE_PATH +"/{id}", ID_PALAVRA)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().contemErro(excEsperada.getMessage()));

        verify(service).findById(ID_PALAVRA, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus200AoAtualizarUmaPalavraExitenteQuandoValido() throws Exception{
        Palavra palavraEsperada = criarPalavraValida();
        PalavraDTO palavraDTOEsperado = criarPalavraDTOValido();
        
        PalavraRequestDTO dto = PalavraRequestDTO.builder().palavra("Solzinho").build();

        BDDMockito.when(service.atualizar(dto.getPalavra(), ID_PALAVRA, ID_CACA_PALAVRAS)).thenReturn(palavraEsperada);
        BDDMockito.when(cacaPalavrasMapper.toPalavraDTO(palavraEsperada)).thenReturn(palavraDTOEsperado);

        mockMvc.perform(put(BASE_PATH +"/{id}", ID_PALAVRA )
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(responseBody().contemObjetoComoJson(palavraDTOEsperado, PalavraDTO.class));

        verify(service).atualizar(dto.getPalavra(), ID_PALAVRA, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus400EErroAoAtualizarAoNaoNaoInformarPalavra() throws Exception{        
        PalavraRequestDTO dto = new PalavraRequestDTO();

        mockMvc.perform(put(BASE_PATH +"/{id}", ID_PALAVRA )
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().contemErro("Palavra não pode ser vazia"));;

        verify(service, never()).atualizar(dto.getPalavra(), ID_PALAVRA, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus204AoDeletar() throws Exception {
        mockMvc.perform(delete(BASE_PATH +"/{id}", ID_PALAVRA)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).delete(ID_PALAVRA, ID_CACA_PALAVRAS);
    }

    @Test
    void deveRetornarStatus204AoDeletarTodasAsPalavrasDeUmCacaPalavras() throws Exception {
        mockMvc.perform(delete(BASE_PATH)
                .contentType(CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).deleteAll(ID_CACA_PALAVRAS);
    }

    private Palavra criarPalavraValida() {
        return Palavra.builder().id(ID_PALAVRA).palavra("Sol").build();
    }

    private PalavraDTO criarPalavraDTOValido(){
        return PalavraDTO.builder().id(ID_PALAVRA).palavra("Sol").build();
    }

}
