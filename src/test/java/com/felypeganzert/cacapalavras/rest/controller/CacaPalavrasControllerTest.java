package com.felypeganzert.cacapalavras.rest.controller;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CacaPalavrasControllerTest {

    @InjectMocks
    private CacaPalavrasController controller;

    @Mock
    private CacaPalavrasService service;

    @Mock
    private CacaPalavrasRepository repository;

    @Mock
    private CacaPalavrasMapper cacaPalavrasMapper;

    @Mock
    private CacaPalavrasPayloadMapper payloadMapper;

    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.criarComBasico(any(CacaPalavrasDTO.class))).thenReturn(criarCacaPalavrasValido());
    }

    @Test
    void deveChamarCriarComBasicoDoServiceComSucesso() {
        BDDMockito.when(payloadMapper.toCacaPalavrasDTO(any(CacaPalavrasPostDTO.class)))
            .thenReturn(criarCacaPalavrasDTOValido());

        CacaPalavrasPostDTO dto = criarCacaPalavrasPostDTOValido();
        controller.criarComBasico(dto);

        Mockito.verify(service).criarComBasico(any(CacaPalavrasDTO.class));
    }

    @Test
    void deveChamarBuscarInformacoesBasicasDoServiceComSucesso() {
        controller.findAllComInformacoesBasicas();

        Mockito.verify(service).findAllComInformacoesBasicas();
    }

    @Test
    void deveChamarFindByIdDoServiceComSucesso() {
        controller.findById(ID_CACA_PALAVRAS);

        Mockito.verify(service).findById(ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarComSucessoDeleteDoServiceComSucesso() {
        controller.delete(ID_CACA_PALAVRAS);

        Mockito.verify(service).delete(ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarComSucessoResolverCacaPalavrasDoServiceComSucesso() {
        controller.solucionar(ID_CACA_PALAVRAS);

        Mockito.verify(service).resolverCacaPalavras(ID_CACA_PALAVRAS);
    }

    private CacaPalavrasPostDTO criarCacaPalavrasPostDTOValido() {
        return CacaPalavrasPostDTO.builder().criador("Teste Criador").titulo("Teste Título").build();
    }

    private CacaPalavrasDTO criarCacaPalavrasDTOValido() {
        return CacaPalavrasDTO.builder().criador("Teste Criador").titulo("Teste Título").build();
    }

    private CacaPalavras criarCacaPalavrasValido(){
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setId(1);
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador("Teste Criador");
        cacaPalavras.setTitulo("Teste Título");
        return cacaPalavras;
    }

}
