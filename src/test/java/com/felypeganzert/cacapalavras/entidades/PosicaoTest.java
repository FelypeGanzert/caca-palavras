package com.felypeganzert.cacapalavras.entidades;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PosicaoTest {

    @Test
    void deveInstanciarComSucessoQuadoCoordenadasForemZero() {
        Posicao posicao = new Posicao(0, 0);

        Assertions.assertThat(posicao).isNotNull();
        Assertions.assertThat(posicao.getPosicaoX()).isEqualTo(0);
        Assertions.assertThat(posicao.getPosicaoY()).isEqualTo(0);
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
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Posicao(-1, 0));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComPosicaoYNegativa() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Posicao(0, -1));
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComCoordenadasNegativas() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Posicao(-1, -1));
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoXForZero() {
        Posicao posicao = criarPosicaoValida();
        posicao.setPosicaoX(0);

        Assertions.assertThat(posicao.getPosicaoX()).isEqualTo(0);
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoYForZero() {
        Posicao posicao = criarPosicaoValida();
        posicao.setPosicaoY(0);

        Assertions.assertThat(posicao.getPosicaoY()).isEqualTo(0);
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
        return new Posicao(0, 0);
    }

}
