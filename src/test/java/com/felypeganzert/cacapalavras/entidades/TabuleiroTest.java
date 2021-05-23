package com.felypeganzert.cacapalavras.entidades;

import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.ALTURA_MINIMA;
import static com.felypeganzert.cacapalavras.entidades.Tabuleiro.LARGURA_MINIMA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TabuleiroTest {

    @Test
    void deveInstanciarComSucessoQuadoDimensoesForemMinimas() {
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);

        assertThat(tabuleiro).isNotNull();
        assertThat(tabuleiro.getAltura()).isEqualTo(ALTURA_MINIMA);
        assertThat(tabuleiro.getLargura()).isEqualTo(LARGURA_MINIMA);
    }

    @Test
    void deveInstanciarComSucessoQuadoDimensoesForemAcimaDaMinima() {
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA + 1, ALTURA_MINIMA + 1);

        assertThat(tabuleiro).isNotNull();
        assertThat(tabuleiro.getAltura()).isEqualTo(ALTURA_MINIMA + 1);
        assertThat(tabuleiro.getLargura()).isEqualTo(LARGURA_MINIMA + 1);
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComAlturaMenorQueAMinima() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA - 1))
                .withMessage("A Altura é menor que a mínima (" + ALTURA_MINIMA + ")");
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComLarguraMenorQueAMinima() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(LARGURA_MINIMA - 1, ALTURA_MINIMA))
                .withMessage("A Largura é menor que a mínima (" + LARGURA_MINIMA + ")");
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComDimensoesMenoresQueAMinima() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Tabuleiro(LARGURA_MINIMA - 1, ALTURA_MINIMA - 1))
                .withMessage("Largura e Altura são menores que as mínimas (" + LARGURA_MINIMA + ", " + ALTURA_MINIMA + ")");
    }

    @Test
    void deveRetornarLetraExistenteDeDeterminadaPosicaoComSucesso(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Letra a = new Letra("a", new Posicao(1, 1));
        tabuleiro.getLetras().add(a);

        Letra letraRetonarda = tabuleiro.getLetraDaPosicao(new Posicao(1, 1));

        assertThat(letraRetonarda).isNotNull();
        assertThat(letraRetonarda.getLetra()).isEqualTo(a.getLetra());
    }

    @Test
    void deveRetornarLetraComMesmaPosicaoPesquisadaComSucesso(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Letra a = new Letra("a", new Posicao(1, 1));
        Letra a2 = new Letra("a", new Posicao(1, 2));
        tabuleiro.getLetras().add(a);
        tabuleiro.getLetras().add(a2);

        Letra letraRetonarda = tabuleiro.getLetraDaPosicao(new Posicao(1, 1));

        assertThat(letraRetonarda).isNotNull();
        assertThat(letraRetonarda.getLetra()).isEqualTo(a.getLetra());
        assertThat(letraRetonarda.getPosicao()).isEqualTo(a.getPosicao());
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoTentarPesquisarPorPosicaoInexistenteComXFora() {
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Posicao posicaoFora = new Posicao(LARGURA_MINIMA + 1, ALTURA_MINIMA + 2);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tabuleiro.getLetraDaPosicao(posicaoFora))
                .withMessage("Posição (" + posicaoFora.getX() + ", " + posicaoFora.getY() + ") não existe no Tabuleiro");
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoTentarPesquisarPorPosicaoExistenteMasSemLetraNela() {
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA + 2, ALTURA_MINIMA + 2);
        Posicao posicaoSemLetra = new Posicao(LARGURA_MINIMA, ALTURA_MINIMA + 1);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tabuleiro.getLetraDaPosicao(posicaoSemLetra))
                .withMessage("Nenhuma letra encontra na Posição (" + posicaoSemLetra.getX() + ", " + posicaoSemLetra.getY() + ")");
    }

    @Test
    void deveRetornarTrueQuandoAPosicaoExistirNoInicioDoTabuleiro(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Posicao posicao = new Posicao(1, 1);

        assertThat(tabuleiro.posicaoExiste(posicao)).isTrue();
    }

    @Test
    void deveRetornarTrueQuandoAPosicaoExistirNoExtremoDoTabuleiro(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Posicao posicao = new Posicao(LARGURA_MINIMA, ALTURA_MINIMA);

        assertThat(tabuleiro.posicaoExiste(posicao)).isTrue();
    }

    @Test
    void deveRetornarFalseQuandoXDaPosicaoForZero() throws Exception{
        Posicao posicaoMock = Mockito.mock(Posicao.class);
        Mockito.when(posicaoMock.getX()).thenReturn(0);
        Mockito.when(posicaoMock.getY()).thenReturn(1);
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);

        assertThat(tabuleiro.posicaoExiste(posicaoMock)).isFalse();
    }

    @Test
    void deveRetornarFalseQuandoYDaPosicaoForZero(){
        Posicao posicaoMock = Mockito.mock(Posicao.class);
        Mockito.when(posicaoMock.getX()).thenReturn(1);
        Mockito.when(posicaoMock.getY()).thenReturn(0);
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);

        assertThat(tabuleiro.posicaoExiste(posicaoMock)).isFalse();
    }

    @Test
    void deveRetornarFalseQuandoXDaPosicaoEstiverForaDoTabuleiro(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Posicao posicao = new Posicao(LARGURA_MINIMA + 1, 1);

        assertThat(tabuleiro.posicaoExiste(posicao)).isFalse();
    }

    @Test
    void deveRetornarFalseQuandoYDaPosicaoEstiverForaDoTabuleiro(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Posicao posicao = new Posicao(1, ALTURA_MINIMA + 1);

        assertThat(tabuleiro.posicaoExiste(posicao)).isFalse();
    }

    @Test
    void deveRetornarTrueParaPosicaoForaDoTabuleiro(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Posicao posicao = new Posicao(LARGURA_MINIMA + 1, ALTURA_MINIMA + 1);

        assertThat(tabuleiro.posicaoNaoExiste(posicao)).isTrue();
    }

    @Test
    void deveRetornarFalseQuandoAPosicaoExistirNoExtremoDoTabuleiro(){
        Tabuleiro tabuleiro = new Tabuleiro(LARGURA_MINIMA, ALTURA_MINIMA);
        Posicao posicao = new Posicao(LARGURA_MINIMA, ALTURA_MINIMA);

        assertThat(tabuleiro.posicaoNaoExiste(posicao)).isFalse();
    }



}
