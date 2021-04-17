package com.felypeganzert.cacapalavras.services;

import java.util.Arrays;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TabuleiroServiceImplTest {

    @InjectMocks
    private TabuleiroServiceImpl tabuleiroService;

    @Test
    void deveValidarComSucessoPosicaoNoInicioDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicao = new Posicao(1, 1);
        tabuleiroService.validaPosicaoNoTabuleiro(tabuleiro, posicao);
    }

    @Test
    void deveValidarComSucessoPosicaoNoExtremoDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicao = new Posicao(tabuleiro.getLargura(), tabuleiro.getAltura());
        tabuleiroService.validaPosicaoNoTabuleiro(tabuleiro, posicao);
    }

    @Test
    void deveGerarIllegalStateExceptionAoValidarPosicaoForaDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicao = new Posicao(tabuleiro.getLargura() + 1, tabuleiro.getAltura() + 1);

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> tabuleiroService.validaPosicaoNoTabuleiro(tabuleiro, posicao));
    }

    @Test
    void deveGerarIllegalStateExceptionAoValidarPosicaoComXForaDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicao = new Posicao(tabuleiro.getLargura() + 1, tabuleiro.getAltura());

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> tabuleiroService.validaPosicaoNoTabuleiro(tabuleiro, posicao));
    }

    @Test
    void deveGerarIllegalStateExceptionAoValidarPosicaoComYForaDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicao = new Posicao(tabuleiro.getAltura(), tabuleiro.getLargura() + 1);

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> tabuleiroService.validaPosicaoNoTabuleiro(tabuleiro, posicao));
    }

    @Test
    void deveLimparComSucessoLetraExistenteEmDeterminadaPosicao() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Posicao posicaoQueSeraLimpada = new Posicao(1, 3);
        Letra a = new Letra(1L, "a", new Posicao(1, 1));
        Letra b = new Letra(2L, "b", new Posicao(1, 2));
        Letra c = new Letra(3L, "c", posicaoQueSeraLimpada);
        tabuleiro.getLetras().addAll(Arrays.asList(a, b, c));

        tabuleiroService.limparPosicaoNoTabuleiro(tabuleiro, posicaoQueSeraLimpada);
        
        Assertions.assertThat(tabuleiro.getLetras()).contains(a, b).doesNotContain(c);
    }

    @Test
    void deveManterLetrasIntactasAoTentarLimparLetraInexistenteEmDeterminadaPosicao() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Letra a = new Letra(1L, "a", new Posicao(1, 1));
        Letra b = new Letra(2L, "b", new Posicao(1, 2));
        tabuleiro.getLetras().addAll(Arrays.asList(a, b));

        tabuleiroService.limparPosicaoNoTabuleiro(tabuleiro, new Posicao(1, 3));

        Assertions.assertThat(tabuleiro.getLetras()).contains(a, b);
    }

    @Test
    void deveRetornarTrueQuandoLetraEstiverEmDetermiandaPosicao() {
        Posicao posicao = new Posicao(1, 1);
        Letra a = new Letra(1L, "a", posicao);

        Assertions.assertThat(tabuleiroService.isLetraNaPosicao(a, posicao)).isTrue();
    }

    @Test
    void deveRetornarFalseQuandoLetraNaoEstiverEmDetermiandaPosicao() {
        Letra a = new Letra(1L, "a", new Posicao(1, 1));

        Assertions.assertThat(tabuleiroService.isLetraNaPosicao(a, new Posicao(1, 2))).isFalse();
    }

    @Test
    void deveInserirLetraEmCelulaComSucessoEmPosicaoVaziaSemAfetarDemaisLetras() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Letra a = new Letra(1L, "a", new Posicao(1, 1));
        Letra b = new Letra(2L, "b", new Posicao(1, 2));
        tabuleiro.getLetras().addAll(Arrays.asList(a, b));

        Letra c = new Letra(3L, "c", new Posicao(1, 3));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, c);

        Assertions.assertThat(tabuleiro.getLetras()).contains(a, b, c);
    }

    @Test
    void deveLimparValorPrevioDaCelulaEInserirNovaLetraComSucessoEmPosicaoVazia() {
        Tabuleiro tabuleiro = criarTabuleiroValido();
        Letra a = new Letra(1L, "a", new Posicao(1, 1));
        Letra b = new Letra(2L, "b", new Posicao(1, 2));
        tabuleiro.getLetras().addAll(Arrays.asList(a, b));

        Letra c = new Letra(3L, "c", new Posicao(1, 2));
        tabuleiroService.inserirLetraEmCelula(tabuleiro, c);

        Assertions.assertThat(tabuleiro.getLetras()).contains(a, c).doesNotContain(b);
    }

    @Test
    void deveGerarIllegalStateExceptionAoTentarInserirLetraEmPosicaoForaDoTabuleiro() {
        Tabuleiro tabuleiro = criarTabuleiroValido();

        Posicao posicao = new Posicao(tabuleiro.getAltura() + 1, tabuleiro.getLargura() + 1);
        Letra a = new Letra(1L, "a", posicao);

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> tabuleiroService.inserirLetraEmCelula(tabuleiro, a));
    }

    private Tabuleiro criarTabuleiroValido() {
        return new Tabuleiro(1L,  Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);
    }

}
