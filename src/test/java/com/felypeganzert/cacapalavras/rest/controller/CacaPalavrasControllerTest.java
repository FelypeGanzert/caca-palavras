package com.felypeganzert.cacapalavras.rest.controller;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
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
    private CacaPalavrasRepository repository;;

    @Mock
    private CacaPalavrasMaper cacaPalavrasMapper;

    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.criarComBasico(any(CacaPalavrasPostDTO.class))).thenReturn(criarCacaPalavrasValido());
    }

    @Test
    void deveChamarCriarComBasicoDoServiceComSucesso() {
        CacaPalavrasPostDTO dto = criarCacaPalavrasPostDTOValido();
        controller.criarComBasico(dto);

        Mockito.verify(service).criarComBasico(any(CacaPalavrasPostDTO.class));
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
        controller.solucionarById(ID_CACA_PALAVRAS);

        Mockito.verify(service).resolverCacaPalavras(ID_CACA_PALAVRAS);
    }

    private CacaPalavrasPostDTO criarCacaPalavrasPostDTOValido() {
        return CacaPalavrasPostDTO.builder().criador("Teste Criador").titulo("Teste Título").build();
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
