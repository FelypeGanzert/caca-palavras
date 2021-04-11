package com.felypeganzert.cacapalavras.entidades;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TabuleiroTest {

    @Test
    void deveInstanciarComSucessoQuadoDimensoesForemMinimas() {
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.ALTURA_MINIMA, Tabuleiro.LARGURA_MINIMA);

        Assertions.assertThat(tabuleiro).isNotNull();
        Assertions.assertThat(tabuleiro.getAltura()).isEqualTo(Tabuleiro.ALTURA_MINIMA);
        Assertions.assertThat(tabuleiro.getLargura()).isEqualTo(Tabuleiro.LARGURA_MINIMA);
    }

    @Test
    void deveInstanciarComSucessoQuadoDimensoesForemAcimaDaMinima() {
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.ALTURA_MINIMA + 1, Tabuleiro.LARGURA_MINIMA + 1);

        assertThat(tabuleiro).isNotNull();
        Assertions.assertThat(tabuleiro.getAltura()).isEqualTo(Tabuleiro.ALTURA_MINIMA + 1);
        Assertions.assertThat(tabuleiro.getLargura()).isEqualTo(Tabuleiro.LARGURA_MINIMA + 1);
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComAlturaMenorQueAMinima() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(Tabuleiro.ALTURA_MINIMA - 1, Tabuleiro.LARGURA_MINIMA));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComLarguraMenorQueAMinima() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(Tabuleiro.ALTURA_MINIMA, Tabuleiro.LARGURA_MINIMA - 1));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComDimensoesMenoresQueAMinima() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(Tabuleiro.ALTURA_MINIMA - 1, Tabuleiro.LARGURA_MINIMA - 1));
    }

}
