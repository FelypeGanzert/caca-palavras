package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.util.CacaPalavrasCreator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CacaPalavrasResolverTest {
    // TODO: Testes essenciais da Classe

    @InjectMocks
    private TabuleiroServiceImpl tabuleiroService;

    @Test
    void printarTabuleiro() {
        // TODO: remover esse teste utilizado para conferir eficiência do algoritmo
        CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadas(tabuleiroService);

        CacaPalavrasResolver resolver = new CacaPalavrasResolver(cacaPalavras);
        resolver.encontrarPalavrasNoTabuleiro();
        resolver.printarTabuleiro(cacaPalavras.getTabuleiro());
    }

}
