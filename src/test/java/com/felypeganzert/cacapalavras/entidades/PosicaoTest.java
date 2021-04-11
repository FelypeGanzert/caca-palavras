package com.felypeganzert.cacapalavras.entidades;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PosicaoTest {

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComCoordenadasIguaisAZero() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Posicao(0, 0));
    }

    @Test
    void deveInstanciarComSucessoQuadoCoordenadasForemPositivas() {
        Posicao posicao = new Posicao(1, 1);

        Assertions.assertThat(posicao).isNotNull();
        assertThat(posicao.getPosicaoX()).isEqualTo(1);
        Assertions.assertThat(posicao.getPosicaoY()).isEqualTo(1);
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComPosicaoXNegativa() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Posicao(-1, 1));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComPosicaoYNegativa() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Posicao(1, -1));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComCoordenadasNegativas() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Posicao(-1, -1));
    }

    @Test
    void  deveGerarIllegalArgumentExceptionAoAtribuirPosicaoXZero() {
        Posicao posicao = criarPosicaoValida();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> posicao.setPosicaoX(0));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoAtribuirPosicaoYZero() {
        Posicao posicao = criarPosicaoValida();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> posicao.setPosicaoY(0));
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoXForPositiva() {
        Posicao posicao = criarPosicaoValida();
        posicao.setPosicaoX(1);

        Assertions.assertThat(posicao.getPosicaoX()).isEqualTo(1);
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoYForPositiva() {
        Posicao posicao = criarPosicaoValida();
        posicao.setPosicaoY(1);

        Assertions.assertThat(posicao.getPosicaoY()).isEqualTo(1);
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoAtribuirPosicaoXNegativa() {
        Posicao posicao = criarPosicaoValida();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> posicao.setPosicaoX(-1));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoAtribuirPosicaoYNegativa() {
        Posicao posicao = criarPosicaoValida();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> posicao.setPosicaoY(-1));
    }

    private Posicao criarPosicaoValida() {
        return new Posicao(1, 1);
    }

}
