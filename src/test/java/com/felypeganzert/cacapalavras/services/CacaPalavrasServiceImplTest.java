package com.felypeganzert.cacapalavras.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CacaPalavrasServiceImplTest {

    @InjectMocks
    private CacaPalavrasServiceImpl cacaPalavrasService;

    @InjectMocks
    private TabuleiroServiceImpl tabuleiroService;

    @Test
    void deveLimparTodasAsLetrasDoTabuleiroComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);

        cacaPalavrasService.limparLetrasDoTabuleiro(cacaPalavras);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    }

    @Test
    void deveLimparTodasAsLetrasDoTabuleiroEAsLocalizacoesDasPalavrasComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);

        cacaPalavrasService.limparLetrasDoTabuleiro(cacaPalavras);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
        assertThat(cacaPalavras.getPalavras()).isNotEmpty();
        for (Palavra palavra : cacaPalavras.getPalavras()) {
            assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
        }
    }

    @Test
    void naoDeveGerarExceptionAoLimparAsLetrasDoTabuleiroSemLetras() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator
                .criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro(tabuleiroService);

        cacaPalavrasService.limparLetrasDoTabuleiro(cacaPalavras);

        assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    }

    @Test
    void deveLimparLocalizacoesDasPalavrasNoTabuleiroComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);

        cacaPalavrasService.limparLocalizacoesDasPalavrasNoTabuleiro(cacaPalavras.getPalavras());
        assertThat(cacaPalavras.getPalavras()).isNotEmpty();
        for (Palavra palavra : cacaPalavras.getPalavras()) {
            assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
        }
    }

}
