package com.felypeganzert.cacapalavras.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CacaPalavrasServiceImplTest {

    @InjectMocks
    private CacaPalavrasServiceImpl service;

    @InjectMocks
    private TabuleiroServiceImpl tabuleiroService;

    @Mock
    private CacaPalavrasResolver resolver;

    @Test
    void deveChamarEncontrarPalavrasNoTabuleiroDoResolverComSucesso(){
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
        
        service.encontrarPalavrasNoTabuleiro(cacaPalavras);
        
        Mockito.verify(resolver).encontrarPalavrasNoTabuleiro(cacaPalavras);
    }

    @Test
    void deveLimparTodasAsLetrasDoTabuleiroComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
        assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();

        service.limparLetrasDoTabuleiro(cacaPalavras);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    }

    @Test
    void deveLimparTodasAsLetrasDoTabuleiroEAsLocalizacoesDasPalavrasComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
        assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();

        service.limparLetrasDoTabuleiro(cacaPalavras);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
        assertThat(cacaPalavras.getPalavras()).isNotEmpty();
        for (Palavra palavra : cacaPalavras.getPalavras()) {
            assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
        }
    }

    @Test
    void naoDeveGerarExceptionAoLimparAsLetrasDoTabuleiroQueNaoContemLetras() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator
                .criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro(tabuleiroService);
        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();

        service.limparLetrasDoTabuleiro(cacaPalavras);
    }

    @Test
    void deveLimparLocalizacoesDasPalavrasNoTabuleiroComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
        assertThat(cacaPalavras.getPalavras()).isNotEmpty();

        service.limparLocalizacoesDasPalavrasNoTabuleiro(cacaPalavras.getPalavras());
        assertThat(cacaPalavras.getPalavras()).isNotEmpty();
        for (Palavra palavra : cacaPalavras.getPalavras()) {
            assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
        }
    }

}
