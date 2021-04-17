package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.assertj.core.api.Assertions;
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

        Assertions.assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    }

    @Test
    void deveLimparTodasAsLetrasDoTabuleiroEAsLocalizacoesDasPalavrasComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);

        cacaPalavrasService.limparLetrasDoTabuleiro(cacaPalavras);

        Assertions.assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
        for (Palavra palavra : cacaPalavras.getPalavras()) {
            Assertions.assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
        }
    };

    @Test
    void naoDeveGerarExceptionAoLimparAsLetrasDoTabuleiroSemLetras() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator
                .criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro(tabuleiroService);

        cacaPalavrasService.limparLetrasDoTabuleiro(cacaPalavras);

        Assertions.assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    };

    @Test
    void deveLimparLocalizacoesDasPalavrasNoTabuleiroComSucesso() {
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);

        cacaPalavrasService.limparLocalizacoesDasPalavrasNoTabuleiro(cacaPalavras.getPalavras());

        for (Palavra palavra : cacaPalavras.getPalavras()) {
            Assertions.assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
        }
    };

}
