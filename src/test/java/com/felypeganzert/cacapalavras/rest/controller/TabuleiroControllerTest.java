package com.felypeganzert.cacapalavras.rest.controller;

import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.ALTURA_MINIMA;
import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.LARGURA_MINIMA;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

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
public class TabuleiroControllerTest {

    @InjectMocks
    private TabuleiroController controller;

    @Mock
    private TabuleiroService service;

    @Mock
    private CacaPalavrasMaper cacaPalavrasMapper;

    private static final int ID_TABULEIRO = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.criarComBasico(ArgumentMatchers.any(TabuleiroPostDTO.class),
                ArgumentMatchers.any(Integer.class))).thenReturn(criarTabuleiroValido());
    }

    @Test
    void deveChamarCriarComBasicoDoServiceComSucesso() {
        TabuleiroPostDTO dto = TabuleiroPostDTO.builder().altura(ALTURA_MINIMA).largura(LARGURA_MINIMA).build();

        controller.criarComBasico(dto, ID_CACA_PALAVRAS);

        Mockito.verify(service).criarComBasico(dto, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarFindByIdDoServiceComSucesso() {
        controller.findById(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).findById(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarDeleteDoServiceComSucesso() {
        controller.delete(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).delete(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    private Tabuleiro criarTabuleiroValido() {
        int largura = Tabuleiro.LARGURA_MINIMA;
        int altura = Tabuleiro.ALTURA_MINIMA;
        Tabuleiro tabuleiro = new Tabuleiro(ID_TABULEIRO, largura, altura);
        return tabuleiro;
    }

}
