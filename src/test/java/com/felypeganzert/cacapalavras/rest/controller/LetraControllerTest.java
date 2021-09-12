package com.felypeganzert.cacapalavras.rest.controller;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;
import com.felypeganzert.cacapalavras.services.LetraService;

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
public class LetraControllerTest {

    @InjectMocks
    private LetraController controller;

    @Mock
    private LetraService service;

    @Mock
    private CacaPalavrasMaper cacaPalavrasMapper;

    private static final int ID_LETRA = 1;
    private static final int ID_TABULEIRO = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.adicionarLetra(
                    ArgumentMatchers.any(Letra.class),
                    ArgumentMatchers.any(Integer.class),
                    ArgumentMatchers.any(Integer.class))
                ).thenReturn(criarLetraValida());
    }

    @Test
    void deveChamarAdicionarPalavraDoServiceComSucesso() {
        LetraPostDTO dto = LetraPostDTO.builder()
                            .letra('c')
                            .posicaoX(1)
                            .posicaoY(1)
                            .build();

        Letra letra = new Letra(dto.getLetra(), new Posicao(dto.getPosicaoX(), dto.getPosicaoY()));
        controller.adicionarLetra(dto, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).adicionarLetra(letra, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarFindAllDoServiceComSucesso() {
        controller.findAll(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).findAll(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarFindByIdDoServiceComSucesso() {
        controller.findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarAtualizarDoServiceComSucesso() {
        LetraPutDTO dto = LetraPutDTO.builder().letra('s').build();
        controller.atualizar(dto, ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).atualizar(dto.getLetra(), ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarDeleteDoServiceComSucesso() {
        controller.delete(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).delete(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarDeleteAllDoServiceComSucesso() {
        controller.deleteAll(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).deleteAll(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    private Letra criarLetraValida() {
        return Letra.builder().id(ID_LETRA).letra('s').build();
    }

}
