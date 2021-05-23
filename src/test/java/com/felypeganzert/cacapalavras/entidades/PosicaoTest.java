package com.felypeganzert.cacapalavras.entidades;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PosicaoTest {

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComCoordenadasIguaisAZero() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Posicao(0, 0))
            .withMessage("Posição X e Y precisam ser positivas");
    }

    @Test
    void deveInstanciarComSucessoQuadoCoordenadasForemPositivas() {
        Posicao posicao = new Posicao(1, 1);

        assertThat(posicao).isNotNull();
        assertThat(posicao.getX()).isEqualTo(1);
        Assertions.assertThat(posicao.getY()).isEqualTo(1);
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComPosicaoXNegativa() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Posicao(-1, 1))
            .withMessage("Posição X precisa ser positiva");
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComPosicaoYNegativa() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Posicao(1, -1))
            .withMessage("Posição Y precisa ser positiva");
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoInstanciarComCoordenadasNegativas() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Posicao(-1, -1))
            .withMessage("Posição X e Y precisam ser positivas");
    }

    @Test
    void  deveGerarIllegalArgumentExceptionAoAtribuirPosicaoXZero() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> posicao.setX(0))
            .withMessage("Posição X precisa ser positiva");
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoAtribuirPosicaoYZero() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> posicao.setY(0))
            .withMessage("Posição Y precisa ser positiva");
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoXForPositiva() {
        Posicao posicao = criarPosicaoValida();
        posicao.setX(1);

        Assertions.assertThat(posicao.getX()).isEqualTo(1);
    }

    @Test
    void deveAtribuirComSucessoQuandoPosicaoYForPositiva() {
        Posicao posicao = criarPosicaoValida();
        posicao.setY(1);

        Assertions.assertThat(posicao.getY()).isEqualTo(1);
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoAtribuirPosicaoXNegativa() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> posicao.setX(-1))
            .withMessage("Posição X precisa ser positiva");
    }

    @Test
    void deveGerarIllegalArgumentExceptionAoAtribuirPosicaoYNegativa() {
        Posicao posicao = criarPosicaoValida();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> posicao.setY(-1))
            .withMessage("Posição Y precisa ser positiva");
    }

    private Posicao criarPosicaoValida() {
        return new Posicao(1, 1);
    }

}
