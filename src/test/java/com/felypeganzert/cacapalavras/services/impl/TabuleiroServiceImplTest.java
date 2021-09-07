package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.ALTURA_MINIMA;
import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.LARGURA_MINIMA;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

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

@ExtendWith(SpringExtension.class)
public class TabuleiroServiceImplTest {

    @InjectMocks
    private TabuleiroServiceImpl service;

    @Mock
    private TabuleiroRepository repository;

    @Mock
    private CacaPalavrasService serviceCacaPalavras;

    private static final int ID_TABULEIRO = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(serviceCacaPalavras.findById(anyInt())).thenReturn(cacaPalavrasValido());
        BDDMockito.when(repository.findById(anyInt())).thenReturn(optionalTabuleiroValido());
        BDDMockito.when(repository.save(any(Tabuleiro.class))).thenReturn(tabuleiroValido());
    }

    @Test
    void deveChamarSaveDoRepositoryComSucesso() {
        TabuleiroPostDTO dto = TabuleiroPostDTO.builder().altura(ALTURA_MINIMA).largura(LARGURA_MINIMA).build();

        service.criarComBasico(dto, ID_CACA_PALAVRAS);

        Mockito.verify(repository).save(ArgumentMatchers.any(Tabuleiro.class));
    }

    @Test
    void deveChamarFindByIdDoRepositoryComSucesso() {
        service.findById(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository).findById(ID_TABULEIRO);
    }

    @Test
    void deveGerarExceptionRecursoNaoEncontradoAoBuscarIdNaoExistente() {
        BDDMockito.when(repository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(TABULEIRO, ID, ID_TABULEIRO);

        Assertions.assertThatExceptionOfType(RecursoNaoEncontradoException.class)
                .isThrownBy(() -> service.findById(ID_TABULEIRO, ID_CACA_PALAVRAS))
                .withMessageContaining(exception.getMessage());
    }

    @Test
    void deveGerarExceptionRecursoNaoPertenceAAoBuscaPorTabuleiroNaoPertencenteAoCacaPalavras() {
        CacaPalavras cacaPalavras2 = new CacaPalavras();
        cacaPalavras2.setId(ID_CACA_PALAVRAS + 1);
        BDDMockito.when(serviceCacaPalavras.findById(ArgumentMatchers.any(Integer.class))).thenReturn(cacaPalavras2);

        RecursoNaoPertenceAException exception = new RecursoNaoPertenceAException(TABULEIRO, CACA_PALAVRAS);

        Assertions.assertThatExceptionOfType(RecursoNaoPertenceAException.class)
                .isThrownBy(() -> service.findById(ID_TABULEIRO, ID_CACA_PALAVRAS))
                .withMessage(exception.getMessage());
    }

    @Test
    void deveChamarDeleteDoRepositoryComSucesso() {
        service.delete(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(repository).delete(ArgumentMatchers.any(Tabuleiro.class));
    }

    private CacaPalavras cacaPalavrasValido() {
        return CacaPalavrasCreator.criarCacaPalavrasValido(ID_CACA_PALAVRAS);
    }

    private Tabuleiro tabuleiroValido() {
        return CacaPalavrasCreator.criarTabuleiroValido(ID_TABULEIRO, cacaPalavrasValido());
    }

    private Optional<Tabuleiro> optionalTabuleiroValido() {
        return Optional.of(tabuleiroValido());
    }

}
