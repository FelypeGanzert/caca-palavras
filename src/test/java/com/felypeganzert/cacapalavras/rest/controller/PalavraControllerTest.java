package com.felypeganzert.cacapalavras.rest.controller;

import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.rest.dto.PalavraPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.PalavraPutDTO;
import com.felypeganzert.cacapalavras.services.PalavraService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PalavraControllerTest {

    @InjectMocks
    private PalavraController controller;

    @Mock
    private PalavraService service;

    @Mock
    private CacaPalavrasMaper cacaPalavrasMapper;

    private static final int ID_PALAVRA = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.adicionarPalavra(ArgumentMatchers.any(String.class),
                ArgumentMatchers.any(Integer.class))).thenReturn(criarPalavraValida());
    }

    @Test
    void deveChamarAdicionarPalavraDoServiceComSucesso() {
        PalavraPostDTO dto = PalavraPostDTO.builder().palavra("Sol").build();

        controller.adicionarPalavra(dto, ID_CACA_PALAVRAS);

        Mockito.verify(service).adicionarPalavra(dto.getPalavra(), ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarFindAllDoServiceComSucesso() {
        controller.findAll(ID_CACA_PALAVRAS);

        Mockito.verify(service).findAll(ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarFindByIdDoServiceComSucesso() {
        controller.findById(ID_PALAVRA, ID_CACA_PALAVRAS);

        Mockito.verify(service).findById(ID_PALAVRA, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarAtualizarDoServiceComSucesso() {
        PalavraPutDTO dto = PalavraPutDTO.builder().id(ID_PALAVRA).palavra("Sol").build();
        controller.atualizar(dto, ID_CACA_PALAVRAS);

        Mockito.verify(service).atualizar(dto, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarDeleteDoServiceComSucesso() {
        controller.delete(ID_PALAVRA, ID_CACA_PALAVRAS);

        Mockito.verify(service).delete(ID_PALAVRA, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarDeleteAllDoServiceComSucesso() {
        controller.deleteAll(ID_CACA_PALAVRAS);

        Mockito.verify(service).deleteAll(ID_CACA_PALAVRAS);
    }

    private Palavra criarPalavraValida() {
        return Palavra.builder().id(ID_PALAVRA).palavra("Sol").build();
    }

}
