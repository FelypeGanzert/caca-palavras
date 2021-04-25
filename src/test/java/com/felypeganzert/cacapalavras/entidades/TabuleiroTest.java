package com.felypeganzert.cacapalavras.entidades;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TabuleiroTest {

    @Test
    void deveInstanciarComSucessoQuadoDimensoesForemMinimas() {
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);

        Assertions.assertThat(tabuleiro).isNotNull();
        Assertions.assertThat(tabuleiro.getAltura()).isEqualTo(Tabuleiro.ALTURA_MINIMA);
        Assertions.assertThat(tabuleiro.getLargura()).isEqualTo(Tabuleiro.LARGURA_MINIMA);
    }

    @Test
    void deveInstanciarComSucessoQuadoDimensoesForemAcimaDaMinima() {
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.LARGURA_MINIMA + 1, Tabuleiro.ALTURA_MINIMA + 1);

        assertThat(tabuleiro).isNotNull();
        Assertions.assertThat(tabuleiro.getAltura()).isEqualTo(Tabuleiro.ALTURA_MINIMA + 1);
        Assertions.assertThat(tabuleiro.getLargura()).isEqualTo(Tabuleiro.LARGURA_MINIMA + 1);
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComAlturaMenorQueAMinima() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA - 1));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComLarguraMenorQueAMinima() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(Tabuleiro.LARGURA_MINIMA - 1, Tabuleiro.ALTURA_MINIMA));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComDimensoesMenoresQueAMinima() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(Tabuleiro.LARGURA_MINIMA - 1, Tabuleiro.ALTURA_MINIMA - 1));
    }

    @Test
    void deveRetornarLetraExistenteDeDeterminadaPosicaoComSucesso(){
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);
        Letra a = new Letra("a", new Posicao(1, 1));
        tabuleiro.getLetras().add(a);

        Letra letraRetonarda = tabuleiro.getLetraDaPosicao(new Posicao(1, 1));

        Assertions.assertThat(letraRetonarda).isNotNull();
        Assertions.assertThat(letraRetonarda.getLetra()).isEqualTo(a.getLetra());
    }

    @Test
    void deveRetornarLetraComMesmaPosicaoPesquisadaComSucesso(){
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);
        Letra a = new Letra("a", new Posicao(1, 1));
        Letra a2 = new Letra("a", new Posicao(1, 2));
        tabuleiro.getLetras().add(a);
        tabuleiro.getLetras().add(a2);

        Letra letraRetonarda = tabuleiro.getLetraDaPosicao(new Posicao(1, 1));

        Assertions.assertThat(letraRetonarda).isNotNull();
        Assertions.assertThat(letraRetonarda.getLetra()).isEqualTo(a.getLetra());
        Assertions.assertThat(letraRetonarda.getPosicao()).isEqualTo(a.getPosicao());
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoTentarPesquisarPorPosicaoInexistenteNoTabuleiro() {
        Tabuleiro tabuleiro = new Tabuleiro(Tabuleiro.LARGURA_MINIMA, Tabuleiro.ALTURA_MINIMA);
        Posicao posicaoFora = new Posicao(Tabuleiro.LARGURA_MINIMA +1, Tabuleiro.ALTURA_MINIMA);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tabuleiro.getLetraDaPosicao(posicaoFora));
    }

}
