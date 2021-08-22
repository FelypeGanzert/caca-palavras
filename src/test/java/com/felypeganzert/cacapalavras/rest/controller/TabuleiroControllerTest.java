package com.felypeganzert.cacapalavras.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;


@ExtendWith(SpringExtension.class)
public class TabuleiroControllerTest {
    
    @InjectMocks
    private TabuleiroController controller;

    @Mock
    private TabuleiroService service;

    @Mock
    private TabuleiroRepository repository;

    @Mock
    private CacaPalavrasMaper cacaPalavrasMapper;

    @BeforeEach
    void setUp(){
        BDDMockito.when(service.criarComBasico(ArgumentMatchers.any(TabuleiroPostDTO.class)))
            .thenReturn(criarTabuleiroValido());

            
        BDDMockito.when(cacaPalavrasMapper.toTabuleiroDTO(ArgumentMatchers.any(Tabuleiro.class)))
            .thenReturn(criarTabuleiroDTOValido());
    }

    @Test
    void deveSalvarTabuleiroComSucesso(){
        TabuleiroPostDTO dto = criarTabuleiroPostDTOValido();
        Integer idCriado = controller.criarComBasico(dto);

        assertThat(idCriado).isNotNull().isEqualTo(criarTabuleiroValido().getId());
        Mockito.verify(service).criarComBasico(ArgumentMatchers.any(TabuleiroPostDTO.class));
    }

    @Test
    void deveEncontrarTabuleiroQuandoExistirComSucesso(){
        BDDMockito.when(service.findById(ArgumentMatchers.any(Integer.class)))
                        .thenReturn(Optional.of(criarTabuleiroValido()));

        TabuleiroDTO dto = controller.findById(1);

        assertThat(dto).isNotNull();
    }

    @Test
    void deveGerarExceptionNotFoundAoBuscarIdNaoExistente(){
        BDDMockito.when(service.findById(ArgumentMatchers.any(Integer.class)))
                        .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                    .isThrownBy(() -> controller.findById(1))
                    .withMessageContaining("Tabuleiro não encontrado");
    }

    private TabuleiroPostDTO criarTabuleiroPostDTOValido(){
        return TabuleiroPostDTO
                    .builder()
                    .largura(Tabuleiro.LARGURA_MINIMA)
                    .altura(Tabuleiro.ALTURA_MINIMA)
                    .build();
    }

    private Tabuleiro criarTabuleiroValido(){
        int largura = Tabuleiro.LARGURA_MINIMA;
        int altura = Tabuleiro.ALTURA_MINIMA;
        Tabuleiro tabuleiro = new Tabuleiro(1, largura, altura);
        return tabuleiro;
    }

    private TabuleiroDTO criarTabuleiroDTOValido(){
        return TabuleiroDTO
                    .builder()
                    .id(1)
                    .largura(Tabuleiro.LARGURA_MINIMA)
                    .altura(Tabuleiro.ALTURA_MINIMA)
                    .build();
    }


}
